package mx.rokegcode.psychologyapp.model.response;

public class BaseResponse<T> {

    private String response;
    private T data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
