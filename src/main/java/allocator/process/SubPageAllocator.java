package allocator.process;

import allocator.unit.Subpage;

/**
 * SubPage分配器
 */
public class SubPageAllocator implements Allocator {

    private final Subpage subpage;

    public SubPageAllocator(Subpage subpage){
        this.subpage = subpage;
    }

    public long allocator(Object o) {
        //如果分配0内存的话，初始化的时候
        if(subpage.getElementSize() == 0){
           return handleBitMapIndex(0);
        }
        //如果没有可分配的容量或者page已经将要被销毁
        if(subpage.getNumAvail() == 0 || !subpage.isDoNotDestroy()){
            return -1;
        }

        final int availBitMapIndex = findNextAvailIndex();

        //位图中
        int arrayIndex =  availBitMapIndex >>> 6;

        int setBitMapIndex = availBitMapIndex & 63;

        long [] bitMap = subpage.getBitmap();

        if(((bitMap[arrayIndex] >>> setBitMapIndex ) & 1L) == 0){

            bitMap[arrayIndex] |= 1L << setBitMapIndex;
            int numAvail = subpage.getNumAvail();
            if(--numAvail == 0){

                //从链表中移除该subPage,表示不可用
                removeFromPool();

            }

        }

        return handleBitMapIndex(availBitMapIndex);
    }

    private void removeFromPool() {

        if(subpage.getPrev() != null && subpage.getNext() != null){

            subpage.getPrev().setNext(subpage.getNext());

            subpage.getNext().setPrev(subpage.getPrev());

            subpage.setPrev(null);

            subpage.setNext(null);


        }


    }

    /**
     * 找到可用的位图索引,首次分配都是0
     * @return
     */
    private int findNextAvailIndex() {

        int nextAvail = subpage.getNextAvail();

        if(nextAvail == 0){

            subpage.setNextAvail(-1);

            return nextAvail;

        }

        //否则返回可用的索引

        //获取所有可用的位图
        final long[] bitmapArray = subpage.getBitmap();

        //位图长度
        final int bitmapLen = subpage.getBitmapLength();

        for (int i = 0; i < bitmapLen; i++) {

            long bits = bitmapArray[i];

            if(~bits != 0){
                //表示还有可用的，则寻找里面可用的位置
                return findNextAvail0(i, bits);
            }

        }

        return 0;

    }

    private int findNextAvail0(int i, long bits) {

        final  int maxNumElems = subpage.getMaxNumElements();

        //获取起始位置，i = 0, 表示从0分配到63, 起始位置为0; 1则从64开始。
        final  int baseVal = i << 6;

        for (int j = 0; j < 64; j++) {

            if((bits & 1) == 0){
                //在baseVal 基础上加 偏移值
                int val = baseVal | j;

                if(val < maxNumElems){

                    return val;

                }else {

                    break;

                }

            }

            //位图右移
            bits  >>>= 1;

        }

        return -1;

    }

    public boolean free(Object head, long handle) {
        return false;
    }

    /**
     * 将内存映射索引存储到位图索引中
     * 0x4000000000000000L = 2^30 = 0x0100...(28位0)，使用该数能够， 31位为符号位。保证高位为1
     * 使用低32位存储 memoryMapIndex: (long) bitMapIdx << 32 | subpage.getMemoryMapIndex();
     *
     * @return
     */
    public long handleBitMapIndex(int bitMapIdx){

        return 0x4000000000000000L | (long) bitMapIdx << 32 | subpage.getMemoryMapIndex();

    }

    public static void main(String[] args) {

    }
}
