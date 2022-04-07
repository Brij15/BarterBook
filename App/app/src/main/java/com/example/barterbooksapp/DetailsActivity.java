package com.example.barterbooksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.barterbooksapp.utlity.BookPostDataModel;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    TextView authorText;
    TextView priceText;
    TextView titleText;
    ImageView bookImage;
    private List<BookPostDataModel> bookPosts ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        titleText = findViewById(R.id.bookdetailTitle);
        authorText = findViewById(R.id.authorDetailText);
        priceText = findViewById(R.id.priceDetailText);
        //bookImage = findViewById(R.id.bookImageDetail1);
        bookPosts = new ArrayList<>();
        initializeTestData();

        //Display slider/carousel images into slider.
        getSliderImageList();

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("BookID")){
            if (!thisIntent.getStringExtra("BookID").isEmpty()){
                String filterValue = thisIntent.getStringExtra("BookID");
                    BookPostDataModel book = getBookByID(filterValue);
                if (!book.getTitle().isEmpty()){
                    titleText.setText(book.getTitle());
                    authorText.setText(book.getAuthor());
                    priceText.setText("$ " + book.getPrice().toString());
                    //bookImage.setImageResource(book.getImage());
                }
            }
        }

    }

    private BookPostDataModel getBookByID(String filterValue){
        BookPostDataModel book = new BookPostDataModel();
        //currently filter by category or Location not both
        for(BookPostDataModel item : bookPosts) {
            if (item.getTitle().equals(filterValue)){
                book = item;
            }
        }
        return book;
    }

    //set images into slider/carousel
    private void getSliderImageList(){
        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();

        for(Integer image : initImageList()) {
            slideModels.add(new SlideModel(image));
        }
        imageSlider.setImageList(slideModels, true);
    }

    private void initializeTestData(){
        //initialize images TEST only
        bookPosts.add(new BookPostDataModel("Gardens Of The moon",R.drawable.book1,"Steven Erikson", "Used Like New", "Aurora", 8.99, "Fantasy and Science Friction" ));
        bookPosts.add(new BookPostDataModel("Algebra and Geometry", R.drawable.book2,"Mark V. Lawson", "Library", "Crimson Ridge", 8.99, "Text Books"));
        bookPosts.add(new BookPostDataModel("Mathematics and The Real World",R.drawable.book3 ,"Zvi Artstein", "Used", "Stanely", 4.99, "Text Books"));
        bookPosts.add(new BookPostDataModel("Fifth Season",R.drawable.book4,"N. K Jemsin", "Used","Crimson Ridge", 6.00 , "Fantasy and Science Friction"));
        bookPosts.add(new BookPostDataModel("Name of the Wind", R.drawable.book5,"Patrick Rothfuss", "Used Like New", "Orilla", 12.99, "Fantasy and Science Friction"));
        bookPosts.add(new BookPostDataModel("What IF", R.drawable.book6,"Randall Munroe", "Used Like New", "CollingWood", 10.00, "Science"));
        bookPosts.add(new BookPostDataModel("Deep Learning With Python", R.drawable.book7 ,"François Chollet", "Used", "CollingWood", 6.50, "Computers"));
        bookPosts.add(new BookPostDataModel("Wise Man's Fear", R.drawable.book8, "Patrick Rothfuss", "New", "Orilla", 14.00, "Fantasy and Science Friction"));
    }

    //Init image list for slider
    private List<Integer> initImageList(){
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.book1);
        imageList.add(R.drawable.book2);
        imageList.add(R.drawable.book3);
        imageList.add(R.drawable.book4);
        imageList.add(R.drawable.book4);

        return imageList;
    }
}