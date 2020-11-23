package allocator.unit;

public class Subpage<T> {

    /**
     * 所属chunk
     */
    final Chunk<T> chunk;

    /**
     * 后继节点
     */
    Subpage<T> next;

    /**
     * 前驱节点
     */
    Subpage<T> prev;

    /**
     * 所属Page的标号
     */
    private final int memoryMapIndex;

    /**
     * 整个Chunk的偏移量
     */
    private final int runOffset;


    /**
     * 页大小
     */
    private final int pageSize;

    /**
     * 均等小块的分配信息
     */
    private long[] bitmap;

    /**
     * 是否释放整个Page
     */
    private boolean doNotDestroy;

    /**
     * 均等切分大小
     */
    int elementSize;

    /**
     * 最多分配
     */
    private int maxNumElements;

    /**
     * 位图信息长度，long的个数
     */
    private int bitmapLength;


    /**
     * 下一个可分配的小块位置信息
     */
    private int nextAvail;

    /**
     * 可用的小块数
     */
    private int numAvail;


    /**
     * 初始为头节点
     * @param initPageSize
     */
    Subpage(int initPageSize){

        chunk = null;

        memoryMapIndex = -1;

        runOffset = -1;

        elementSize = -1;

        this.pageSize = initPageSize;

        bitmap = null;

    }

    /**
     * 初始化SubPage
     * @param head
     * @param chunk
     * @param memoryMapIndex
     * @param runOffset
     * @param pageSize
     * @param elementSize
     */
    Subpage(Subpage<T> head, Chunk<T> chunk, int memoryMapIndex, int runOffset,
            int pageSize, int elementSize){
        this.chunk = chunk;
        this.memoryMapIndex = memoryMapIndex;
        this.runOffset = runOffset;
        this.pageSize = pageSize;
        //如果pagaSize = 8k, bitmap = new long[3]
        bitmap = new long[pageSize >>> 10];
        init(head, elementSize);

    }

    /**
     * 初始化
     * @param head
     * @param elementSize
     */
    private void init(Subpage<T> head, int elementSize) {
        doNotDestroy = true;
        this.elementSize = elementSize;
        if(elementSize != 0){

            //设 pageSize = 8k, elementSize = 512B, maxNum = numAvail = 16;
            this.maxNumElements = this.numAvail = pageSize / elementSize;

            this.nextAvail = 0;

            // 16 = 00010000 , bitmapLength = 0;
            this.bitmapLength = this.maxNumElements >>> 6;
            //小于64， bitmapLength +1
            if((this.maxNumElements & 63) != 0){
                ++this.bitmapLength;
            }

            //初始化bitmap
            for(int i = 0; i < this.bitmapLength; ++i) {
                this.bitmap[i] = 0L;
            }

        }

        this.addToPool(head);

    }

    /**
     * 添加到Arena中的双向链表
     * @param head
     */
    private void addToPool(Subpage<T> head) {

        this.prev = head;

        this.next = head.next;

        this.next.prev = this;
        head.next = this;

    }



}
