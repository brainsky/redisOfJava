package unit;

public class Chunk<T> {

    private Arena<T> arena;

    private Chunk<T> prev;

    private Chunk<T> next;

    private ChunkList<T> parent;

    /**
     * 实际内存块
     */
    private T memory;

    /**
     * 分配信息map
     */
    private byte[] memoryMap;

    /**
     * 深度信息map
     */
    private byte[] depthMap;

    /**
     * subpage节点数组
     */
    private Subpage<T>[] subpages;

    /**
     * 分配subpage
     */
    private int subpageOverflowMask;

    /**
     * 页大小
     */
    private int pageSize;

    /**
     * 从1左移到页大小的位置
     */
    private int pageShifts;

    /**
     * 最大高度
     */
    private int maxOrder;

    /**
     * chunk默认大小
     */
    private int chunkSize;


    private int log2ChunkSize;

    /**
     * 可分配最大层数
     */
    private int maxSubpageAllocs;

    /**
     * 标记节点不可用
     */
    private byte unusable;
    /**
     * 可分配字节数
     */
    private int freeBytes;



}
