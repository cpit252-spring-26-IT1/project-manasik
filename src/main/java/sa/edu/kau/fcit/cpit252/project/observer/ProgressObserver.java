package sa.edu.kau.fcit.cpit252.project.observer;


public interface ProgressObserver {

    void onProgressChanged(ProgressEvent event, int currentIndex);
}