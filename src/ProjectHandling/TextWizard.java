import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import java.awt.*;


public class TextWizard  {
    private static final TextTags[] textTags;

    static{
        textTags = new TextTags[3];
        String[] attrbutes = {"src"};

        // hypettext
        textTags[0] = new TextTags("hp", attrbutes);
        textTags[0].setTagrunner(new TagRunner() {
            @Override
            public Text run(String[][] arguments, String content, Project pro) throws TextFormattingException  {
                if(!(arguments.length == 1) || !arguments[0][0].equals("src") || (arguments[0].length != 2) ){
                    throw new TextFormattingException("Wrong argument in HyperTextTag");
                }

                if(arguments[0][1] == null){
                    throw new TextFormattingException("Empty Content");
                }

                return new HyperText(content, arguments[0][1], pro);
            }
        });

        // new line
        textTags[1] = new TextTags("nl", null);
        textTags[1].setTagrunner(new TagRunner() {
            @Override
            public Text run(String[][] arguments, String content, Project pro) throws TextFormattingException {
                if (arguments!= null){
                    throw new TextFormattingException("Wrong argument in HyperTextTag");
                }

                return new Text("\n");

            }
        });

        // bold Text
        textTags[2] = new TextTags("bold", null);
        textTags[2].setTagrunner(new TagRunner() {
            @Override
            public Text run(String[][] arguments, String content, Project pro) throws TextFormattingException {
                if (arguments!= null){
                    throw new TextFormattingException("Wrong argument in HyperTextTag");
                }

                Text t = new Text(content);
                t.setStyle("-fx-font-weight: bold");

                return t;
            }
        });
    }

    private enum mode{
        REGULAR, STARTTAG, ENDTAG, TAGCONTENT;
    }

    private enum tag{
        ATTRIBUTE, CONTENT, NAME;
    }


    // code that takes writable string and formats it into read only displayable String

    public static Text[] textAsDisplayable(String editableString, Project projectsource) throws TextFormattingException{
            Text[] t = new Text[0];
            int stop;
            int start;
            String s = "";
            char c;
            String tagContent = "";
            TextWizard.mode mod = mode.REGULAR; // 0 = regular, 1 = tagreading, 2 = '' reading


            for(int i = 0; i<editableString.length(); i++) {
                c = editableString.charAt(i);
                if(i != editableString.length() -1) {
                    switch (mod) {

                        case REGULAR:
                            if (c == '<') { // if character '<'
                                // make new Text object with all previous String segment
                                t = makeSpace(t);
                                t[t.length - 1] = new Text(s);
                                s = "";

                                // set mode to starttag-reading
                                mod = mode.STARTTAG;
                            } else {
                                s += c;
                            }
                            break;

                        case TAGCONTENT:
                            if (c != '<') {
                                s += c;
                            } else {
                                t = makeSpace(t);

                                // Text newT = decode(tagcontent, string);
                                //HyperText txt = new HyperText(s, tagContent, projectsource);
                                Text txt = decode(tagContent, s, projectsource);

                                t[t.length - 1] = txt;
                                mod = mode.ENDTAG;
                            }
                            break;

                        case STARTTAG:
                            if (c == '>') {
                                tagContent = s;
                                s = "";
                                mod = mode.TAGCONTENT;
                            }else{
                                s += c;
                            }
                            break;

                        case ENDTAG:
                            if (c == '>') {
                                tagContent = "";
                                s = "";
                                mod = mode.REGULAR;
                            }else{
                                s += c;
                            }

                            break;

                        default:
                            break;

                    }
                }else{
                    switch (mod){
                        case REGULAR:
                            s += c;
                            System.out.println("" + s);
                            t = makeSpace(t);
                            t[t.length - 1] = new Text(s);
                            break;
                        case STARTTAG:
                            throw new TextFormattingException("String ends in STARTAG MODE");

                        case ENDTAG:
                            if(c!= '>') {
                                throw new TextFormattingException("String ends in ENDTAG mode");
                            }else{
                                tagContent = "";
                                s="";
                            }
                            break;

                        case TAGCONTENT:
                            throw new TextFormattingException("String ends in TAGCONTENT mode");

                    }

                }
            }

            return t;
    }

    private static  Text[] makeSpace(Text[] t){
        if (t.length != 0){
            Text[] newT = new Text[t.length + 1];

            for (int i = 0; i < t.length; i++) {
                newT[i] = t[i];
            }

            return newT;
        }else{
            return new Text[1];
        }
    }
    private static String[][] makeSpace(String[][] tab){
        String[][] temp;
        if(tab.length != 0) {
             temp = new String[tab.length + 1][2];

            for (int i = 0; i < tab.length; i++) {
                temp[i][0] = tab[i][0];
                temp[i][1] = tab[i][1];
            }


        }else{
            temp = new String[1][2];
        }

        return temp;
    }

    private static  Text decode(String tagcontent, String text, Project pro) throws TextFormattingException{
        String s = "";
        char c;
        TextWizard.tag mod = tag.NAME;

        String[][] attributes = new String[0][0];
        String attribute = "";
        String content = null;
        String name = "";

        for(int i = 0; i<tagcontent.length(); i++){
            c = tagcontent.charAt(i);

            switch (mod) {
                case ATTRIBUTE:
                    if (c == '=' && attribute.equals("")) {
                        throw new TextFormattingException("Missing attribute");
                    }else if( c == '='){
                        attributes = makeSpace(attributes);
                        attributes[attributes.length -1][0] = attribute;

                        attribute = "";
                        mod = tag.CONTENT;
                    }else if (c == ' ') {
                        if(!attribute.equals("")) {
                            throw new TextFormattingException("Space in attributename");
                        }
                    }else {
                        attribute += c;
                    }
                    break;

                case CONTENT:
                    if(content == null){
                        if(c != '\"'){
                            System.out.println("." + c + ".");
                            throw new TextFormattingException("Content fault");
                        }

                        content = "";
                    }else{
                        if(c == '"'){
                            attributes[attributes.length-1][1] = content;
                            content = "";
                            mod = tag.ATTRIBUTE;

                        }else{
                            content += c;
                        }

                    }
                    break;

                case NAME:
                   if(c == '='){
                        throw  new TextFormattingException("You used '=' in tagname");
                    } else if (c== ' ' && !name.equals("")){
                        mod = tag.ATTRIBUTE;
                    }else{
                       name += c;
                   }
                    break;
            }



        }

        if(mod == tag.CONTENT) {
            if(content != null) {
                if (!content.equals("")) {
                    attributes[attributes.length - 1][1] = content;
                    content = "";
                } else {
                    throw new TextFormattingException("Empty attribute");
                }
            }else{
                throw new TextFormattingException("Empty Attribute");
            }
        }else if(mod == tag.ATTRIBUTE){
            if (!attribute.equals("")){
                throw new TextFormattingException("Empty attribute");
            }
        }


        if(attributes.length == 0){
            attributes = null;
        }

        return run(name, attributes, text, pro);
    }

    private static Text run(String name, String[][] att ,String content,Project pro) throws TextFormattingException{
        int index = -1;
        for(int i =0; i<textTags.length; i++){
            if(textTags[i].getName().equals(name)){
                index = i;
                break;
            }
        }

        if(index == -1){
            throw new TextFormattingException("Unknown tag");
        }

        return textTags[index].runTag(content, att, pro);
    }



}
