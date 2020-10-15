package serverClasses.requests;

import model.Language;
import utilities.ServerRequest;

import java.io.Serializable;

public class LanguageFetchRequest implements Serializable{

        private Language obj;
        public LanguageFetchRequest()
        {

        }
        public Language getLang(){
            return  obj;
        }
        public void setLang(Language obj){
            this.obj=obj;
        }
        @Override
        public String toString() {
            return String.valueOf(ServerRequest.LANGUAGE_SHOW);
        }
    }


