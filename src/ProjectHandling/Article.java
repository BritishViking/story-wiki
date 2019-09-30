public class Article {
    private String head;
    private ArticleEntry[] body = null;
    private int articleNUmber;
    private boolean editHead = false;
    private CategoryEntry entries;
    private boolean editBody = false;

    public Article(String head, ArticleEntry[] body, int articleNUmber, CategoryEntry catEntries){
        this.head = head;
        this.body = body;
        this.articleNUmber = articleNUmber;
        this.entries = catEntries;
    }
    public Article(String head, int index){
        this.head = head;
        this.articleNUmber = index;

    }

    public boolean isEditBody(){
        return editBody;
    }
    public boolean isEditHead() {
        return editHead;
    }

    public String getHead(){
        return head;
    }

    public ArticleEntry[] getBody(){
        return body;
    }

    public CategoryEntry getCategoryEntries(){
        return entries;
    }

    public int getArticleNUmber(){
        return articleNUmber;
    }

    public void setHead(String head){
        editHead = true;
        this.head = head;
    }

    public void addEntry(ArticleEntry newEntry){
        expandLIst();
        body[body.length - 1 ] = newEntry;

    }


    private void expandLIst(){
        if(body != null) {


            ArticleEntry[] temp = new ArticleEntry[body.length + 1];
            for (int i = 0; i < body.length; i++) {
                temp[i] = body[i];
            }

            body = temp;
        }else{
            body = new ArticleEntry[1];
        }
    }
    public String toString(){
        String s = articleNUmber + ": " + head ;
        if(entries != null){
            s += "\n// // \nCategoryody: " + entries;
        }else{
            s += "\nCategory: No Category /";
        }
        s += "\n//  // ";

        if(body != null) {
            System.out.println("----------------------------"+body.length);
            for (int i = 0; i < body.length; i++) {
                s += "\n" + body[i].toString();
            }
        }


        return s;
    }

    public static void main(String[]args){
       /* ArticleEntry n = new ArticleEntry("Hei", "Dette er en test", 0);
        ArticleEntry e = new ArticleEntry("Yo", "Dette er enda en test", 1);
        ArticleEntry r = new ArticleEntry("Lo", "Dette er en test igjen 3", 2);

        ArticleEntry[] v = {n,e,r};

        String[] att = {"Name", "Age", "Familiy"};
        CategoryMeta cat = new CategoryMeta("Person", att, 1);
        CategoryEntry ent = new CategoryEntry(cat);

        ent.addEntry("Name", "Septer");
        ent.addEntry("Age", "27");

        Article a = new Article("TEST", v, 2, ent);

        System.out.println(a);

        boolean val1 = false;
        boolean val2 = false;

        System.out.println(!(val1^val2));

         val1 = false;
         val2 = true;

        System.out.println(val1^val2);

        val1 = true;
        val2 = true;

        System.out.println(!(val1^val2));*/

    }

}
