package model.storage;

public interface StorageObserver {
    void onStorageSizeChanged(int currentSize);
}
