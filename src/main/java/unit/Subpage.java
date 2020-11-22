package unit;

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

    Subpage(Subpage<T> head, Chunk<T> chunk, int memoryMapIndex, int runOffset,
            int pageSize, int elementSize){
        this.chunk = chunk;
        this.memoryMapIndex = memoryMapIndex;
        this.runOffset = runOffset;
        this.pageSize = pageSize;
        bitmap = new long[pageSize >>> 10];
        init(head, elementSize);

    }

    private void init(Subpage<T> head, int elementSize) {
        doNotDestroy = true;
        this.elementSize = elementSize;
        if(elementSize != 0){

        }

    }

}
