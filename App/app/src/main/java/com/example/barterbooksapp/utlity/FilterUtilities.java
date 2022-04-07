package com.example.barterbooksapp.utlity;


import com.example.barterbooksapp.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class FilterUtilities {

    public static List<BookPostDataModel> getSearchData(String searchText, List<BookPostDataModel> mainList) {
        List<BookPostDataModel> filteredList = new ArrayList<>();
        if (!searchText.isEmpty()){
            for(BookPostDataModel item : mainList) {
                if (item.getTitle().toLowerCase().contains(searchText.toLowerCase())){
                    filteredList.add(item);
                }
            }
        }

        return filteredList;
    }

    public static List<BookPostDataModel> filterBy(String filterValue, MainActivity.FilterType type, List<BookPostDataModel> mainList ){
        List<BookPostDataModel> filteredList = new ArrayList<>();
        //currently filter by category or Location not both
        if (type.equals(MainActivity.FilterType.CATEGORY)){
            for(BookPostDataModel item : mainList) {
                if (item.getCategory().equals(filterValue)){
                    filteredList.add(item);
                }
            }
        }
        else if(type.equals(MainActivity.FilterType.LOCATION)){
            for(BookPostDataModel item : mainList) {
                if (item.getLocation().equals(filterValue)){
                    filteredList.add(item);
                }
            }
        }
        return filteredList;
    }
}
