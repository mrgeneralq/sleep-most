package me.mrgeneralq.sleepmost;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Tests
{
    public static void main(String[] args)
    {
        //okay so back to following the alg, it should stop after the first comparision  cyabeyes
       String testRemoteVersion = "2.9.9.9.9.99";
       String testCurrentVersion = "4.6";

       /*
       okay all the test cases I can think of *work*
       great :D
       yes but now, the fun fact, It doesn't work in the test script, and it's IDENTICAL
       let's see :D
        */


        System.out.println("Output: " + hasUpdate(testCurrentVersion, testRemoteVersion));
    }

    //okay not that small, I forgot the equalize method
    private static boolean hasUpdate(String local, String remote)
    {
        if (local.equals(remote))
            return false;


        List<Integer> splittedCurrentVersion = Arrays.stream(local.split("\\."))
                .map(Integer::parseInt)
                .collect(toList());

        List<Integer> splittedCachedVersion = Arrays.stream(remote.split("\\."))
                .map(Integer::parseInt)
                .collect(toList());

        equalizeSizes(splittedCachedVersion, splittedCurrentVersion);


        for(int i = 0; i < splittedCachedVersion.size(); i++){

            Integer current = splittedCurrentVersion.get(i);
            Integer cached = splittedCachedVersion.get(i);

            // cached is bigger
            System.out.println(cached + " " + current);
            if(cached > current)
                return true;

            // cached is not equal
            if(cached < current)
               return false;
            }

        return false;
    }

    private static void equalizeSizes(List<Integer> splittedLocalVersion, List<Integer> splittedRemoteVersion)
    {
        int localSize = splittedLocalVersion.size();
        int cachedSize = splittedRemoteVersion.size();

        int zerosAmount = Math.max(localSize, cachedSize) - Math.min(localSize, cachedSize);

        for(int i = 1; i <= zerosAmount; i++)
        {
            if(localSize < cachedSize)
                splittedLocalVersion.add(0);
            else
                splittedRemoteVersion.add(0);
        }
    }
}
