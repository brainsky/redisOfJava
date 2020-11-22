package unit;

public class ChunkList<T> {

    /**
     * 所属arena
     */
    private final Arena arena;

    /**
     * 最小内存使用率
     */
    private int minUsage;

    /**
     * 最大使用率
     */
    private int maxUsage;

    private int maxCapacity;

    /**
     * 头部节点
     */
    private Chunk<T> head;

    /**
     * 下一个状态
     */
    private ChunkList<T> nextList;

    /**
     * 上一个状态
     */
    private ChunkList<T> prevList;
}
