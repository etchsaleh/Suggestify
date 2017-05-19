package suggestify;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.apache.commons.lang.*;
/**
 * Created by HazemTheKing on 5/17/17.
 */
public class IndexedRecommender {
    private HashMap<String, ArrayList<String>> lis = null;

    public IndexedRecommender(String tsvFile){
        lis = parseSimilarArtists(tsvFile);
    }

    private HashMap<String, ArrayList<String>> parseSimilarArtists(String tsvFile)
    {
        HashMap<String, ArrayList<String>> ret = new HashMap<>();
        String line = "";
        String cvsSplitBy = "\t";
        try (BufferedReader br = new BufferedReader(new FileReader(tsvFile))) {
            while ((line = br.readLine()) != null) {

                String[] entry = line.split(cvsSplitBy);

                if((entry[0]).equals((entry[1])))
                    continue;
//                System.out.println(entry[0] + " " + entry[1]);
                if(ret.containsKey((entry[0])))
                {
                    ret.get((entry[0])).add((entry[1]));
                }
                else
                {
                    ArrayList<String> x = new ArrayList<>();
                    x.add((entry[1]));
                    ret.put((entry[0]), x);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return ret;
    }
    private boolean thresChecker(String A, String B){
        String[] A_words = A.split(" ");
        String[] B_words = B.split(" ");
        if(A_words.length != B_words.length)
            return false;
        int thres = A_words.length;
        if(thres == 1)
            return A.equals(B);
        for(int i = 0; i < A_words.length; i++)
            if(A_words[i].equals(B_words[i]))
                thres--;
        return thres <= 1;
    }
    public ArrayList<String> getSimilarTo(String artist, int N){
        if(N > 10)
            throw new IllegalArgumentException("Number of artists can't be larger than 10");
        artist = artist.toLowerCase();
        ArrayList<String> ret = new ArrayList<>();
        ArrayList<String> sim = lis.get(artist);
        for(int i = 0; i < 10; i++){
            boolean x = false;
            String maybe = (sim.get(i));
            for(int j = 0; j < ret.size(); j++)
                x |= thresChecker((ret.get(j)), maybe);
            x|= thresChecker(artist, maybe);
            if(!x)
                ret.add((sim.get(i)));
            if(ret.size() == N)
                break;
        }
        for(int i = 0; i < ret.size(); i++)
        {
            String ars = ret.get(i);
            ars = WordUtils.capitalize(ars);
            ret.set(i, ars);
        }
        return ret;
    }
    private ArrayList<String> cleanList(ArrayList<String> arts)
    {
        HashMap<String, Integer> duparts = new HashMap<>();
        ArrayList<String> filteredArtists = new ArrayList<>();
        boolean[] alref = new boolean[arts.size()];
        for(int i = 0; i < alref.length; i++)
            alref[i] = false;
        for(int i = 0; i < arts.size(); i++)
        {
            if(alref[i] == true)
                continue;
            String s = arts.get(i);
            if(s.contains("www."))
                continue;
            if(s.contains("ft"))
                continue;
            if(s.contains("vs"))
                continue;
            if(s.contains("greatest hits"))
                continue;
            if(s.contains(".com"))
                continue;
            if(s.contains("f."))
                continue;
            if(s.contains("feat"))
                continue;
            if(s.contains("["))
                continue;
            if(s.contains("("))
                continue;
            if(s.contains("-"))
                continue;
            if(s.contains(" live"))
                continue;
            if(s.contains("live "))
                continue;
            if(s.contains("anda"))
                continue;
            if(s.contains("*"))
                continue;
            if(s.contains("\""))
                continue;
            if(s.contains(";"))
                continue;
            if(s.contains("&"))
                continue;
            if(s.contains("\\"))
                continue;
            if(s.contains("/"))
                continue;
            if(s.contains(" and "))
                continue;
            if(s.contains(","))
                continue;
            if(s.contains("@"))
                continue;
            if(s.contains("$"))
                continue;
            if(s.contains(" aka "))
                continue;
            if(s.contains(" with "))
                continue;
            if(s.contains("tribute"))
                continue;
            if(s.contains(" band "))
                continue;
            if(!(s.charAt(0) >= 'a' && s.charAt(0) <= 'z'))
                continue;

            filteredArtists.add(s);
        }

        for(int i = 0; i < filteredArtists.size(); i++)
        {
            String sfilt = filteredArtists.get(i).replaceAll("[^a-zA-Z\\s]", "");
            if(sfilt.length() > 0 && sfilt.charAt(0) == ' ')
            {
                char[] x = sfilt.toCharArray();
                for(int z = 1; z < x.length; z++)
                    x[z-1] = x[z];
                sfilt = new String(x, 0, x.length-1);
            }
            if(!duparts.containsKey(sfilt))
            {
                duparts.put(sfilt, 0);
            }
            int oldv = duparts.get(sfilt);
            duparts.put(sfilt, oldv+1);
        }

        HashMap<String, Integer> deffup = new HashMap<>();
        for(int i = 0; i < filteredArtists.size(); i++)
        {
            if(duparts.containsKey(filteredArtists.get(i)) && duparts.get(filteredArtists.get(i)) > 1)
                deffup.put(filteredArtists.get(i), 1);

        }
        ArrayList<String> filt = new ArrayList<>();
        for(int i = 0; i < filteredArtists.size(); i++)
        {
            String sfilt = filteredArtists.get(i).replaceAll("[^a-zA-Z\\s]", "");
            if(sfilt.length() > 0 && sfilt.charAt(0) == ' ')
            {
                char[] x = sfilt.toCharArray();
                for(int z = 1; z < x.length; z++)
                    x[z-1] = x[z];
                sfilt = new String(x, 0, x.length-1);
            }

            if(!(deffup.containsKey(sfilt) && !sfilt.equals(filteredArtists.get(i))))
                filt.add(filteredArtists.get(i));
        }
        return filt;
    }
    public List<String> getArtists()
    {
        ArrayList<String> ret = cleanList(new ArrayList<>(lis.keySet()));
        for(int i = 0; i < ret.size(); i++)
        {
            String s = ret.get(i);
            ret.set(i, WordUtils.capitalize(s));
        }
        return ret;
    }

}