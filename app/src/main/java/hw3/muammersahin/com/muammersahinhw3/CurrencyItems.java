package hw3.muammersahin.com.muammersahinhw3;

public class CurrencyItems {
    private int id;
    private String from, to, result;

    public CurrencyItems(int id, String from, String to, String result) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Convertion: " +
                "ID: " + id +
                " | FROM: '" + from + '\'' +
                " | TO:'" + to + '\'' +
                " | RESULT:'" + result + '\'' +
                '\n';
    }
}
