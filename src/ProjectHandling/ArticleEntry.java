public class ArticleEntry {
    private String head;
    private String body;
    private boolean edited = false;
    private boolean isWritten = false;
    private int nr;
    private boolean deleted = false;

    public ArticleEntry(String head, String body, int nr){
        this.head = head;
        this.body = body;
        this.nr = nr;
    }

    public String getHead() {
        return head;
    }
    public int getNr(){
        return nr;
    }

    public String getBody() {
        return body;
    }
    public boolean isWritten(){
        return isWritten;
    }
    public boolean isDeleted(){
        return deleted;
    }

    public void setWritten(boolean written){
        isWritten = written;
    }
    public boolean isEdited(){
        return edited;
    }

    public void setBody(String body) {
        if(body == null || this.body == null){
            if(body != null){
                this.body = body;
            }
        }else if(!body.equals(this.body)) {
            System.out.println("edited body " + head);
            edited = true;
            this.body = body;
        }
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    public void setHead(String head){
        if(head == null || this.head == null){
            if(head != null){
                this.head = head;
            }
        }else if(!head.equals(this.head)){
            System.out.println("edited head" + head);
            edited = true;
            this.head = head;
        }
    }



    public String toString(){
        return nr + ": " +head + " // " + body + " // edited: " + edited + "// written: " + isWritten + " // deleted: " + deleted;
    }



    public static void main(String[]args){
        ArticleEntry n = new ArticleEntry("Hei", "Dette er en test", 0);
        ArticleEntry e = new ArticleEntry("Yo", "Dette er enda en test", 1);
        ArticleEntry r = new ArticleEntry("Lo", "Dette er en test igjen 3", 2);

        n.setBody("Dette er ny test");

        System.out.println(n + "\n" + e  +  "\n" + r);

        boolean val = false;
        boolean val2 = false;


    }
}
