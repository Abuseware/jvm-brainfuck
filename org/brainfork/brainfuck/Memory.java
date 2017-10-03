package org.brainfork.brainfuck;

public class Memory {
    private char[] memory = new char[300000];
    private int memoryPtr = 0;

    public void Next() {
        if(++memoryPtr > memory.length - 1)
            memoryPtr = 0;
    }

    public void Prev() {
        if(--memoryPtr < 0)
            memoryPtr = memory.length - 1;
    }

    public void Inc() {
        memory[memoryPtr] += 1;
    }

    public void Dec() {
        memory[memoryPtr] -= 1;
    }

    public void Set(char cell) {
        memory[memoryPtr] = cell;
    }

    public char Get() {
        return memory[memoryPtr];
    }

    public boolean SetPtr(int index) {
        if(index < 0 || index > memory.length -1) {
            return false;
        }

        memoryPtr = index;
        return true;
    }

    public int GetPtr() {
        return memoryPtr;
    }
}
