package com.groupB.Frontend.observer;

import java.util.Observer;

public interface Observable {
    void addObserver(Observer o);

    void notifyObservers();
}
