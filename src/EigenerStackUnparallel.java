public class EigenerStackUnparallel<T>
{
    int m_size;
    T[] m_data;
    int m_stackTop = 0; //0-m_size -1

    public EigenerStackUnparallel(int maxsize){
        m_size = maxsize;
        m_data = (T[])new Object[maxsize];
    }

    public boolean isEmpty ()
    {
        return m_stackTop < 0;
    }


    public boolean isFull ()
    {
        return m_stackTop >= m_size - 1;
    }


    public boolean push (T toPush)
    {
        if(isFull())
        {
            return false;
        }
        m_stackTop++;
        m_data[m_stackTop] = toPush;
        return true;
    }


    public T pop ()
    {
        if(isEmpty())
        {
            return null;
        }

        m_stackTop--;
        return m_data[m_stackTop + 1];
    }
}
