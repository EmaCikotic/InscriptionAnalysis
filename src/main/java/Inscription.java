import com.fasterxml.jackson.annotation.JsonProperty;

public class Inscription {

    private String id;
    private String content;
    @JsonProperty("block_no")
    private long blockNo;
    private long number;
    private long timestamp;
    @JsonProperty("content_length")
    private long contentLength;
    private long value;

    public Inscription() {
    }

    public  Inscription(String id, String content, long blockNo, long number,
                            long timestamp, long contentLength, long value) {
        this.id=id;
        this.content=content;
        this.blockNo=blockNo;
        this.number=number;
        this.timestamp=timestamp;
        this.contentLength=contentLength;
        this.value=value;
    }


    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public long getBlockNo() {
        return blockNo;
    }

    public long getNumber() {
        return  number;
    }

    public long getTimestamp() {
        return  timestamp;
    }

    public long getContentLength() {
        return  contentLength;
    }

    public long getValue(){
        return  value;
    }
}
