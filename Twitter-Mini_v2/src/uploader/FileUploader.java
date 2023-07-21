package uploader;

import java.io.File;

public abstract class FileUploader implements Runnable {
    protected File source;
    protected Integer uploaded;
    protected Integer total;

    public FileUploader() {
        this.total = 0;
        this.uploaded = 0;
    }

    public FileUploader(File source) {
        this.source = source;
        this.total = 0;
        this.uploaded = 0;
    }

    public File getSource() {
        return source;
    }

    public FileUploader setSource(File source) {
        this.source = source;
        return this;
    }
}
