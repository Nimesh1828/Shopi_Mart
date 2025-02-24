package com.example.qrbarcode;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.qrbarcode.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private NavController navController;
    private FirebaseFirestore db;
    private CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance(); // Initialize Firestore instance
        cartManager = new CartManager(); // Ensure you have a CartManager instance ready

        initializeNavigation();
        resetCartOnLogin(); // Reset cart every time user logs in or starts the app
    }

    private void initializeNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }
    }

    private void resetCartOnLogin() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            cartManager.clearCart(); // Clear the cart using the CartManager
        }
    }

    // Example function to fetch and add a product to the cart from Firestore
    private void fetchProductFromFirebase(String barcode) {
        db.collection("products")
                .document(barcode)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        try {
                            Product product = documentSnapshot.toObject(Product.class);
                            if (product != null) {
                                cartManager.addProduct(product);
                                Log.d(TAG, "Product added to cart: " + product.getName());
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error processing product data", e);
                        }
                    } else {
                        Toast.makeText(this, "Product not found in database", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Error fetching product from Firestore", e));
    }

    // Optionally, update UI or navigate to cart after adding items
    private void updateCartUI() {
        // Navigation or UI update logic here
        Toast.makeText(this, "Cart updated, navigate to cart!", Toast.LENGTH_SHORT).show();
    }
}
