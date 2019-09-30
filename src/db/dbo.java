

import java.sql.*;

public class dbo {


    public Article getArticle(String articleName, Project project){
        Connection con = DBConnect.getConnection();

        PreparedStatement stmt = null;
        ResultSet res = null;

        String sql = "SELECT artHode.artid, navn, nr, overskrift, kropp FROM  artHode LEFT JOIN artKropp ON artHode.artid = artKropp.artid WHERE navn = ? AND proid = ?";

        Article article;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, articleName);
            stmt.setInt(2, project.getId());
            res = stmt.executeQuery();
            res.next();
            res.last();
            int size = res.getRow();
            if(size != 0) {
                String name = res.getString("navn");
                int index = res.getInt("artid");
                res.beforeFirst();

                ArticleEntry[] art = new ArticleEntry[size];

                String overskrift;
                String kropp;

                while (res.next()) {
                    overskrift =  res.getString("overskrift");
                    kropp = res.getString("kropp");
                    if(overskrift != null) {
                        art[res.getRow() - 1] = new ArticleEntry(overskrift, kropp, res.getInt("nr"));
                        art[res.getRow() - 1].setWritten(true);
                    }
                }


                if(art[0] == null){
                    art = null;
                }
                article = new Article(name, art, index , getCategoryEntries(articleName, project));

                return article;
            }else{
                return null;
            }
        }catch (Exception e){
            CleanUp.handleException(e);
            return null;
        }finally {
            CleanUp.closeRes(res);
            CleanUp.closePre(stmt);
            CleanUp.closeCon(con);
        }

    }

    /**
     * @param name the search input
     * @param project the project context
     * @return List of article names
     */
    public String[] searchByName(String name, Project project){
        Connection con = DBConnect.getConnection();

        PreparedStatement stmt = null;
        ResultSet res = null;

        String sql = "SELECT navn FROM artHode WHERE navn like ?  AND proid = ? ORDER BY navn;";
        String stringSQL = "%" + name + "%";

        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, stringSQL);
            stmt.setInt(2, project.getId());
            res = stmt.executeQuery();

            res.last();
            int size = res.getRow();
            res.beforeFirst();

            String[] result = new String[size];

            for(int i =0; i<result.length; i++){
                res.next();
                result[i] = res.getString("navn");
            }
            return result;
        }catch (Exception e){
            CleanUp.handleException(e);
            return null;
        }finally {
            CleanUp.closeRes(res);
            CleanUp.closePre(stmt);
            CleanUp.closeCon(con);
        }
    }



    public CategoryEntry getCategoryEntries(String articleName, Project project){
        Connection con = DBConnect.getConnection();

        PreparedStatement stmt = null;
        ResultSet res = null;


        String sql = "SELECT kategori.navn, kategori.katid ,katatt.attid,  katatt.attnavn, entry  FROM  artHode JOIN artkat ON artHode.artid = artkat.artid JOIN kategori ON kategori.katid = artkat.katid LEFT JOIN katatt ON kategori.katid = katatt.katid LEFT JOIN katattinnlegg ON katatt.katid = katattinnlegg.katid AND katatt.attid = katattinnlegg.attid AND artHode.artid = katattinnlegg.artid WHERE  artHode.navn = ? AND kategori.proid = ? ";
        CategoryEntry entries = null;
        CategoryMeta meta = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, articleName);
            stmt.setInt(2, project.getId());
            res = stmt.executeQuery();
            res.next();
            res.last();
            int size = res.getRow();
            if(size != 0) {
                String name = res.getString("kategori.navn");
                int index = res.getInt("kategori.katid");
                res.beforeFirst();

                CategroyAttribute[] attr = new CategroyAttribute[size];
                String[] values = new String[size];
                CategroyAttribute[] att = new CategroyAttribute[8];

                while (res.next()) {
                    attr[res.getRow() - 1] = new CategroyAttribute(res.getString("katatt.attnavn"), res.getInt("katatt.attid"));
                    values[res.getRow()-1] = res.getString("entry");
                }
                meta = new CategoryMeta(name, attr, index);
                entries = new CategoryEntry(meta);
                entries.setEntries(values);


                return entries;
            }else{
                return null;
            }
        }catch (Exception e){
            CleanUp.handleException(e);
            return null;
        }finally {
            CleanUp.closeRes(res);
            CleanUp.closePre(stmt);
            CleanUp.closeCon(con);
        }
    }

    public void saveArticle(Article art){

        System.out.println("boutta save");
        // saves the Article name if that is changed
       if(art.isEditHead()){
           changeName(art.getHead(), art.getArticleNUmber()); //  todo not done
       }



        // saves the article body
       ArticleEntry[] entries = art.getBody();
       if(entries != null) {
           ArticleEntry[] temp = new ArticleEntry[entries.length];
           int index = 0;
           for (int i = 0; i < entries.length; i++) {
               if (!entries[i].isDeleted() || entries[i].isWritten()) {
                   if (entries[i].isEdited() || entries[i].isDeleted()) {
                       temp[index] = entries[i];
                       index++;
                   }
               }
           }
           System.out.println("Index: " + index);

           ArticleEntry[] editedEntries = new ArticleEntry[index];
           for (int i = 0; i < index; i++) {
               editedEntries[i] = temp[i];
           }

           if (index != 0) {
               System.out.println("Hei");
               setBody(art.getArticleNUmber(), editedEntries);
           }
       }


       // saves the category entries
        if(art.getCategoryEntries() != null)
       setCategoryEntry(art.getCategoryEntries(), art.getArticleNUmber());

    }

    public boolean setCategoryEntry(CategoryEntry ent, int artIndex){
        DisplayableCatAttEntry[] dEnt = ent.getDisplayableEntries();

        String sql = "";
        Connection con = DBConnect.getConnection();
        PreparedStatement stmt = null;

        try {

            for(int i = 0; dEnt.length>i; i++) {

                if(dEnt[i].isEdited()) {

                    if (dEnt[i].isWritten()) {
                        //UPDATE artKropp SET kropp = "Jonisburg have existed a little longer than 2 minute", overskrift = "Historay" WHERE artid = 2 and nr = 1;
                        sql = "UPDATE katattInnlegg SET entry = ?  WHERE artid = ? and katid = ? and attid = ? ";
                    } else {
                        sql = "INSERT INTO katattInnlegg(entry, artid, katid, attid) VALUES (?, ?, ?, ?);";

                    }

                    System.out.println(sql);

                    stmt = con.prepareStatement(sql);

                    DisplayableCatAttEntry en = dEnt[i];
                    stmt.setString(1, en.getValue());
                    stmt.setInt((2), artIndex);
                    stmt.setInt((3), en.getCategory().getMetaIndex());
                    stmt.setInt((4), en.getPhysicalIndex());

                    stmt.executeUpdate();


                }
            }

            return true ;
        }catch (Exception e){
            CleanUp.handleException(e);
            return false;
        }finally {
            CleanUp.closePre(stmt);
            CleanUp.closeCon(con);
        }


     //   String sql = "U";
        // todo push entries

    }

    // TODO not done
    public void changeName(String newArtName, int artIndex){
        // find the article;
        // if it exists, set name to new name;
        // if fail return false;
    }

    public boolean setBody( int artIndex, ArticleEntry[] editedEntries){
        String sql = "";
        Connection con = DBConnect.getConnection();
        PreparedStatement stmt = null;

        try {


            for(int i = 0; i<editedEntries.length; i++){
                if(!editedEntries[i].isDeleted()) {
                    if (editedEntries[i].isWritten()) {
                        //UPDATE artKropp SET kropp = "Jonisburg have existed a little longer than 2 minute", overskrift = "Historay" WHERE artid = 2 and nr = 1;
                        sql = "UPDATE artKropp SET overskrift = ?,  kropp = ?  WHERE artid = ? and nr = ?;";
                    } else {
                        sql = "INSERT INTO artKropp(overskrift, kropp, artid, nr) VALUES (?, ?, ?, ?);";
                        System.out.println("EDITING");
                    }

                    stmt = con.prepareStatement(sql);

                    ArticleEntry en = editedEntries[i];
                    stmt.setString(1, en.getHead());
                    stmt.setString((2), en.getBody());
                    stmt.setInt((3), artIndex);
                    stmt.setInt((4), en.getNr());

                    stmt.executeUpdate();
                }else{
                    System.out.println("Deleted " + editedEntries[i].getHead());
                    sql = "DELETE FROM  artKropp  WHERE artid = ? and nr = ?;";
                    stmt = con.prepareStatement(sql);

                    ArticleEntry en = editedEntries[i];
                    stmt.setInt(1, artIndex);
                    stmt.setInt((2), en.getNr());

                    stmt.executeUpdate();
                }
            }



            return true;

        }catch (Exception e){
            CleanUp.handleException(e);
            return false;
        }finally {
            CleanUp.closePre(stmt);
            CleanUp.closeCon(con);
        }

    }

    public Article createNewArticle(String artname, CategoryMeta meta, Project project){
        Connection con = DBConnect.getConnection();

        PreparedStatement stmt = null;
        ResultSet res = null;


        String sql = "INSERT INTO artHode VALUES(DEFAULT , ?, ? ) ";

        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, artname);
            stmt.setInt(2, project.getId());
            stmt.executeUpdate();

            sql= "SELECT artid FROM artHode WHERE navn = ? AND proid = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1,artname);
            stmt.setInt(2,project.getId());
            res =stmt.executeQuery();
            res.next();
            int index = res.getInt("artid");





            if(meta != null) {
                sql = "INSERT INTO artkat(katid, artid) VALUES (?,?);";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1,meta.getMetaIndex());
                stmt.setInt(2,index);
                stmt.executeUpdate();
                return new Article(artname, null, index, new CategoryEntry(meta));
            }else{
                return new Article(artname, null, res.getInt("artid"), null);
            }


        }catch (Exception e){
            CleanUp.handleException(e);
            return null;
        }finally {
            CleanUp.closeRes(res);
            CleanUp.closePre(stmt);
            CleanUp.closeCon(con);
        }
    }

    public CategoryMeta findCategory(String catname, Project pro){
        System.out.println("Entered find category");
        if(catname == null){
            System.out.println("Returned null");
            return null;
        }
        Connection con = DBConnect.getConnection();

        PreparedStatement stmt = null;
        ResultSet res = null;


        String sql = "SELECT kategori.navn, kategori.katid , katatt.attid, katatt.attnavn  FROM  kategori LEFT JOIN katatt ON kategori.katid = katatt.katid  WHERE  kategori.navn = ? AND proid = ?";

        CategoryMeta meta = null;
        try {
            stmt = con.prepareStatement(sql);
            System.out.println("Input: "+ catname);
            stmt.setString(1, catname);
            stmt.setInt(2, pro.getId());
            res = stmt.executeQuery();
            res.last();
            int size = res.getRow();
            System.out.println("Size: " + size);
            if(size != 0) {
                String name = res.getString("kategori.navn");
                int index = res.getInt("kategori.katid");
                res.first();

                CategroyAttribute[] attr = new CategroyAttribute[size];

                if(!(res.getString("katatt.attnavn")==null)) {

                    res.beforeFirst();
                    while (res.next()) {

                        attr[res.getRow() - 1] = new CategroyAttribute(res.getString("katatt.attnavn"), res.getInt("katatt.attid"));
                    }
                }else{

                    attr = null;
                }
                meta = new CategoryMeta(name, attr, index);



                return meta;
            }else{
                System.out.println("Returned null pga size");
                return null;
            }
        }catch (Exception e){
            System.out.println("Returned null pga expetion");
            CleanUp.handleException(e);
            return null;
        }finally {
            CleanUp.closeRes(res);
            CleanUp.closePre(stmt);
            CleanUp.closeCon(con);
        }
    }

    public boolean deleteArticle(int articleNumber){
       /*
       DELETE FROM artKropp WHERE artid = 4;



;
        */


        Connection con = DBConnect.getConnection();
        PreparedStatement stmt = null;


        try {

                String sql = "DELETE FROM artKropp WHERE artid = ?";
                stmt = con.prepareStatement(sql);
                stmt.setInt((1),articleNumber);
                stmt.executeUpdate();

                sql = "DELETE FROM katattinnlegg WHERE artid = ?;";
                stmt = con.prepareStatement(sql);
                stmt.setInt((1),articleNumber);
                stmt.executeUpdate();

                sql = "DELETE FROM artkat WHERE artid = ?; ";
                stmt = con.prepareStatement(sql);
                stmt.setInt((1),articleNumber);
                stmt.executeUpdate();

                sql = "DELETE FROM tag WHERE artid = ?;";
                stmt = con.prepareStatement(sql);
                stmt.setInt((1),articleNumber);
                stmt.executeUpdate();

                sql = "DELETE FROM artHode WHERE artid = ?";
                stmt = con.prepareStatement(sql);
                stmt.setInt((1),articleNumber);
                stmt.executeUpdate();


            return true ;
        }catch (Exception e){
            CleanUp.handleException(e);
            return false;
        }finally {
            CleanUp.closePre(stmt);
            CleanUp.closeCon(con);
        }
    }

    /**
     *
     * @param suggestion the partial category suggestion
     * @param pro the project context
     * @return the categories that mach the suggestion
     */

    public CategoryMeta[] findCategories(String suggestion, Project pro){


        if(suggestion == null){
            System.out.println("Returned null");
            return null;
        }

        Connection con = DBConnect.getConnection();
        PreparedStatement stmt = null;
        ResultSet res = null;


        String sql = " SELECT kategori.navn, kategori.katid ,katatt.attid, katatt.attnavn  FROM  kategori  LEFT JOIN katatt ON kategori.katid = katatt.katid  where kategori.navn LIKE ? AND kategori.proid = ?;";
        CategoryMeta[] meta = null;
        try {

            stmt = con.prepareStatement(sql);

            suggestion = "%" + suggestion + "%";
            stmt.setString(1, suggestion);
            stmt.setInt(2, pro.getId());
            res = stmt.executeQuery();
            res.last();
            int size = res.getRow();
            res.beforeFirst();

            if(size != 0) {
                meta = new CategoryMeta[size];
                res.next();


                CategroyAttribute[] attr = new CategroyAttribute[size];
                String name = res.getString("kategori.navn");

                attr[res.getRow() - 1] = new CategroyAttribute(res.getString("katatt.attnavn"), res.getInt("katatt.attid"));

                int katindex = res.getInt("kategori.katid");
                int metaIndex = 0;
                int attIndex = 0;
                CategroyAttribute[] newAtt;

                while(res.next()){
                    if(katindex == res.getInt("kategori.katid")){
                        attIndex++;
                        attr[attIndex] = new CategroyAttribute(res.getString("katatt.attnavn"), res.getInt("katatt.attid"));

                    }else {

                        newAtt = new CategroyAttribute[attIndex + 1];
                        if (attr[0].getAttributeName() == null) {
                            System.out.println(name + " is null ");
                                newAtt = null;
                        } else{
                            System.out.println(name + " is not null ");
                            for (int i = 0; i < newAtt.length; i++) {
                                newAtt[i] = attr[i];
                            }
                        }
                        meta[metaIndex]= new CategoryMeta(name, newAtt, katindex);

                        attr[0] =  new CategroyAttribute(res.getString("katatt.attnavn"), res.getInt("katatt.attid"));
                        attIndex = 0;
                        metaIndex++;
                        katindex = res.getInt("kategori.katid");
                        name = res.getString("kategori.navn");
                    }
                }

                 newAtt = new CategroyAttribute[attIndex+1];

                for(int i = 0; i< newAtt.length; i++){
                    newAtt[i] = attr[i];
                }
                meta[metaIndex]= new CategoryMeta(name, newAtt, katindex);

                CategoryMeta[] newMeta = new CategoryMeta[metaIndex+1];

                for(int i = 0; i<newMeta.length; i++){
                    newMeta[i]= meta[i];
                }


                return newMeta;
            }else{
                System.out.println("Returned null pga size");
                return null;
            }
        }catch (Exception e){
            System.out.println("Returned null pga expetion");
            CleanUp.handleException(e);
            return null;
        }finally {
            CleanUp.closeRes(res);
            CleanUp.closePre(stmt);
            CleanUp.closeCon(con);
        }
    }

    public CategoryMeta createNewCategory(String name, String[] attributes, Project pro){
        Connection con = DBConnect.getConnection();

        PreparedStatement stmt = null;
        ResultSet res = null;


        String sql = "INSERT INTO kategori VALUES(DEFAULT , ?, ?) ";

        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, pro.getId());
            stmt.executeUpdate();

            sql= "SELECT katid FROM kategori  WHERE navn = ? AND proid = ? ORDER BY katid DESC";
            stmt = con.prepareStatement(sql);
            stmt.setString(1,name);
            stmt.setInt(2, pro.getId());
            res =stmt.executeQuery();
            res.next();
            int index = res.getInt("katid");

            CategroyAttribute[] newA = new CategroyAttribute[attributes.length];

            for(int i = 0; i<attributes.length; i++) {
                sql = "call addNewAttribute(?,? );";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, index);
                stmt.setString(2, attributes[i]);
                stmt.executeUpdate();

                sql = "SELECT MAX(attid) AS at FROM katatt WHERE katid = ? ";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1,index);
                res = stmt.executeQuery();
                res.next();
                newA[i] = new CategroyAttribute(attributes[i], res.getInt("at"));
            }


            return  new CategoryMeta(name, newA, index);


        }catch (Exception e){
            CleanUp.handleException(e);
            return null;
        }finally {
            CleanUp.closeRes(res);
            CleanUp.closePre(stmt);
            CleanUp.closeCon(con);
        }
    }

    public CategoryMeta updateCategory(CategoryMeta oldCategory){
        Connection con = DBConnect.getConnection();
        PreparedStatement stmt = null;
        int d = oldCategory.DELETED;
        int n = oldCategory.NEWATTR;
        int e = oldCategory.EDITED;


        String sql = "UPDATE kategori SET navn = ? WHERE katid = ?";
        try {
            if(oldCategory.isNameEdit()) {
                stmt = con.prepareStatement(sql);
                stmt.setString(1, oldCategory.getName());
                stmt.setInt(2, oldCategory.getMetaIndex());
                stmt.executeUpdate();
            }
            for(int i = 0; i<oldCategory.getAttributeNumber(); i++){
                int operation = oldCategory.getAttributeStatus(i);

                if(operation==d){
                    System.out.println("delete");
                    sql = "call deleteAttribute(?,?)";
                    stmt = con.prepareStatement(sql);
                    stmt.setInt(1,oldCategory.getMetaIndex() );
                    stmt.setInt(2, oldCategory.getAttributeObject(i).getAttributeIndex());
                    stmt.executeUpdate();
                 }else if(operation == n){
                    // add new attribute
                    sql = "call addNewAttribute(?,?)";
                    stmt = con.prepareStatement(sql);
                    stmt.setInt(1,oldCategory.getMetaIndex() );
                    stmt.setString(2, oldCategory.getAttributeObject(i).getAttributeName());
                    stmt.executeUpdate();
                }else if(operation == e){
                    // edit attribute
                    System.out.println("edit");
                    sql = "UPDATE  katatt SET attnavn = ? WHERE attid = ? and katid = ?  ";
                    stmt = con.prepareStatement(sql);

                    stmt.setString(1, oldCategory.getAttribute(i));
                    stmt.setInt(2, oldCategory.getAttributeObject(i).getAttributeIndex());
                    stmt.setInt(3,oldCategory.getMetaIndex() );
                    stmt.executeUpdate();
                }
            }


        }catch (SQLException f) {
            f.printStackTrace();
            return null;
        }finally {

            CleanUp.closePre(stmt);
            CleanUp.closeCon(con);
        }
        // update name
        // update excisting attributes
        // create new attributes
        return null;
    }

    public static void main (String[]args){

        dbo db  = new dbo();
        Project pro = new Project("Test", 1);
        db.findCategory("Person", pro);
       /* ArticleEntry n = new ArticleEntry("Hei", "Dette er en test", 0);
        ArticleEntry e = new ArticleEntry("kl", "Dette er enda en test", 3);
        ArticleEntry r = new ArticleEntry("Lo", "Dette er en test igjen 3", 2);
        n.setWritten(true);
        n.setBody("dd");
        e.setBody("oo");
        r.setBody("fh");

        ArticleEntry[] v = {r,e};

        String[] att = {"Name", "Age", "Familiy"};
        CategoryMeta cat = new CategoryMeta("Person", att, 1);
        CategoryEntry ent = new CategoryEntry(cat);*/






    }
}


// when

