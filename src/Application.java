public class Application {

    public static void main(String[] args){
        String a = "1,Anna,Allen,f,07.11.1998,German,Hamburg,stipend,1,present,anna.allen98@gmail.com";
        String[] b = a.split(",");
        for(String word:b){
            System.out.println(word);
        }

    }
}
