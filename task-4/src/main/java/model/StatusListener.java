package model;

public interface StatusListener {
    void onStatusUpdate(String name, boolean isBusy);
}