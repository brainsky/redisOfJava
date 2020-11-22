package unit;

import java.util.List;

/**
 * 为每个线程分配的逻辑区域
 */
public class Arena<T> {

    private final  int maxOrder;

    private final int pageSize;

    private final int pageShift;

    private final int chunkSize;

    /**
     * 用于判断是否为small/tiny
     */
    private final int subpageOverflowMask;

    /**
     * small请求双链表头个数
     */
    int numSmallSubpagePools;

    /**
     * 对齐基准
     */
    int directMemoryCacheAlignment;

    /**
     * 用于内存对齐
     */
    int directMemoryCacheAlignmentMask;

    Subpage<T>[]  tinySubpagePools;

    Subpage<T>[]  smallSubpagPools;

    /**
     * 分配器
     */
    Allocator   parent;


    ChunkList<T> q050;

    ChunkList<T> q025;

    ChunkList<T> q000;

    ChunkList<T> qInit;

    ChunkList<T> q075;

    ChunkList<T> q100;

    enum SizeClass{
        Tiny,
        Small,
        Normal;
    }


}
