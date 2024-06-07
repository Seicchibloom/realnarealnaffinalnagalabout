package com.example.signuploginrealtime;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookingPage extends Fragment {

    private Spinner spinnerChoices;
    private TextView description;
    private TextView price;
    private TextView placename;
    private EditText userEmail;
    private DatePicker date;
    private Button buttonSave;
    private Button buttonDelete;
    private ImageView choiceImage;

    private String[] places = {"Tagaytay","Manila","Boracay","Palawan","Davao"};
    private String[] choices = {"Wind and Sea at Wind Residence", "Bayview Park Hotel", "La Carmela de Boracay Hotel", "Blue Lagoon Inn & Suites", "Daylight Inn"};

    //DESCRIPTIONS
    private String[] descriptions =
            {"The accommodation comes with a fully equipped kitchenette with a microwave and kettle, a flat-screen TV, and a private bathroom with bidet and shower. Each unit includes air conditioning, and some units at the apartment complex have a balcony.",
                    "Modern rooms come with earth colour tones and are bathed in warm light. Each unit is equipped with a cable TV, personal safe, and an attached bathroom with shower facilities and toiletries.",
                    "Situated in Boracay and with White Beach Station 2 reachable within 70 metres, La Carmela de Boracay Hotel features concierge services, non-smoking rooms, a restaurant, free WiFi and a bar.",
                    "Air-conditioned rooms are equipped with a flat-screen TV, seating area and minibar. En suite bathrooms include hot/cold shower facilities with free toiletries. Rooms also feature a balcony overlooking the pool and garden.",
                    "Daylight Inn in Davao City, Philippines offers a range of entertainment facilities that will help you unwind and relax during your stay. After a long day of exploring the city, why not treat yourself to a soothing massage at the hotel's spa?"};

    private String[] prices = {"PRICE: ₱ 1,593", "PRICE: ₱ 2,573", "PRICE: ₱ 9,974", "PRICE: ₱ 1,064", "PRICE: ₱ 375"};
    private int[] images = {R.drawable.windmainpic, R.drawable.bayview, R.drawable.boracayhotel, R.drawable.palawanhotel, R.drawable.davaohotel};

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersReference;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_page, container, false);

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("users");

        // Initialize UI components
        placename = view.findViewById(R.id.placename);
        spinnerChoices = view.findViewById(R.id.spinner_choices);
        description = view.findViewById(R.id.description);
        price = view.findViewById(R.id.price);
        userEmail = view.findViewById(R.id.userEmail);
        date = view.findViewById(R.id.datepick);
        buttonSave = view.findViewById(R.id.button);
        buttonDelete = view.findViewById(R.id.delete_btn);
        choiceImage = view.findViewById(R.id.choice_image);

        // Set up Spinner with adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, choices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChoices.setAdapter(adapter);

        spinnerChoices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                placename.setText(places[position]);
                description.setText(descriptions[position]);
                price.setText(prices[position]);
                choiceImage.setImageResource(images[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmailText = userEmail.getText().toString().trim();
                if (!userEmailText.isEmpty()) {
                    // Get the selected date from DatePicker
                    int day = date.getDayOfMonth();
                    int month = date.getMonth() + 1; // Month starts from 0, so add 1
                    int year = date.getYear();

                    // Format the date properly
                    String formattedDate = String.format("%d-%02d-%02d", year, month, day);

                    // Save data to Firebase
                    saveToFirebase(userEmailText, placename.getText().toString(), description.getText().toString(), price.getText().toString(), formattedDate);
                } else {
                    Toast.makeText(getContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmailText = userEmail.getText().toString().trim();
                if (!userEmailText.isEmpty()) {
                    deleteFromFirebase(userEmailText);
                } else {
                    Toast.makeText(getContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private String encodeEmail(String email) {
        // Replace '.' with ',' to create a valid key
        return email.replace(".", ",");
    }

    private void saveToFirebase(String userEmail, String place, String description, String price, String date) {
        try {
            String encodedEmail = encodeEmail(userEmail);
            DatabaseReference bookingsReference = firebaseDatabase.getReference("bookings").child(encodedEmail).push();
            String bookingId = bookingsReference.getKey();
            Booking booking = new Booking(place, description, price, date);

            bookingsReference.setValue(booking)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Booking saved successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failed to save booking", Toast.LENGTH_SHORT).show();
                            Log.e("BookingPage", "Error: " + task.getException().getMessage());
                        }
                    });
        } catch (Exception e) {
            Log.e("BookingPage", "Error saving to Firebase: ", e);
        }
    }

    private void deleteFromFirebase(String userEmail) {
        try {
            String encodedEmail = userEmail.replace(".", ",");
            DatabaseReference bookingsReference = firebaseDatabase.getReference("bookings").child(encodedEmail);

            bookingsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        bookingsReference.removeValue()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Bookings deleted successfully!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Failed to delete bookings", Toast.LENGTH_SHORT).show();
                                        Log.e("BookingPage", "Error: " + task.getException().getMessage());
                                    }
                                });
                    } else {
                        Toast.makeText(getContext(), "No bookings found for the given email", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Error checking bookings: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("BookingPage", "Error: " + databaseError.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e("BookingPage", "Error deleting from Firebase: ", e);
        }
    }


    public static class Booking {
        public String place;
        public String description;
        public String price;
        public String date;

        public Booking() {
            // Default constructor required for calls to DataSnapshot.getValue(Booking.class)
        }

        public Booking(String place, String description, String price, String date) {
            this.place = place;
            this.description = description;
            this.price = price;
            this.date = date;
        }
    }
}
