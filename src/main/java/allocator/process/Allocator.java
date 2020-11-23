package allocator.process;

/**
 * 分配器
 */
public interface Allocator<T> {


    /**
     * 分配对象
     * @param t
     * @return
     */
    long allocator(T t);

    /**
     * 正常删除
     * @param head
     * @param handle
     * @return
     */
    boolean free(T head, long handle);

}
