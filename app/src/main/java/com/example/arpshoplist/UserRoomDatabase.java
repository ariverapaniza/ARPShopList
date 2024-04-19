package com.example.arpshoplist;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.arpshoplist.login.User;
import com.example.arpshoplist.login.UserDao;
import com.example.arpshoplist.ingredients.Ingredients;
import com.example.arpshoplist.ingredients.IngredientsDao;
import com.example.arpshoplist.recipe.Recipe;
import com.example.arpshoplist.recipe.RecipeDao;
import com.example.arpshoplist.recipe.RecipeIngredient;
import com.example.arpshoplist.recipe.RecipeIngredientDao;
import com.example.arpshoplist.shoppinglist.ShoppingList;
import com.example.arpshoplist.shoppinglist.ShoppingListDao;
import com.example.arpshoplist.shoppinglist.ShoppingListItem;
import com.example.arpshoplist.shoppinglist.ShoppingListItemDao;
import com.example.arpshoplist.shoppinglist.ShoppingListItemInList;
import com.example.arpshoplist.shoppinglist.ShoppingListItemInListDao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Recipe.class, Ingredients.class, RecipeIngredient.class, ShoppingListItem.class, ShoppingList.class, ShoppingListItemInList.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract RecipeDao recipeDao();
    public abstract IngredientsDao ingredientsDao();
    public abstract RecipeIngredientDao recipeIngredientDao();
    public abstract ShoppingListItemDao shoppingListItemDao();
    public abstract ShoppingListDao shoppingListDao();
    public abstract ShoppingListItemInListDao shoppingListItemInListDao();


    private static volatile UserRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UserRoomDatabase.class, "user_database")
                            .addCallback(roomCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            populateInitialData(INSTANCE);
            Log.i("XYZ", "onOpen Called");
        }
    };

    private static void populateInitialData(UserRoomDatabase instance) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            UserDao userDao = INSTANCE.userDao();
            userDao.insert(new User("student1", "Student One", "user1@ait.com", encryptPassword("password1")));
            userDao.insert(new User("student2", "Student Two", "user2@ait.com", encryptPassword("password2")));
            userDao.insert(new User("student3", "Student Three", "user3@ait.com", encryptPassword("password3")));
            userDao.insert(new User("student4", "Student Four", "user4@ait.com", encryptPassword("password4")));
            userDao.insert(new User("professor1", "Professor One", "professor1@ait.com", encryptPassword("password5")));
            RecipeDao recipeDao = INSTANCE.recipeDao();
            recipeDao.insert(new Recipe(1, "Pizza", "Spoon on the tomato sauce, sprinkle with cheese, and place your desired toppings on the pizza. Be careful not to overload the pizza with too many toppings, or your pizza will be soggy."));
            recipeDao.insert(new Recipe(2, "Apple Crumble", "Fill your house with the warm, comforting scent of apple crumble. Tender baked apples delicately spiced with cinnamon and a sweet and crumbly topping. \nServed with a generous dollop of cream or custard, it’s the ultimate comfort dessert."));
            recipeDao.insert(new Recipe(3, "Roast pork with apple sauce", "Pat the pork rind dry with paper towel. Place the pork on a rack in a baking dish. Rub the rind with oil, then salt. \nRoast for about 40 minutes, or until skin blisters. Reduce oven temperature to 180°C (160°C fan-forced) and roast for a further 40 minutes or until pork is just cooked."));
            recipeDao.insert(new Recipe(4, "Chicken Caesar Pasta", "Pat the chicken dry with paper towels. Sprinkle evenly with 1 teaspoon of the salt and 1/4 teaspoon of the pepper. \nHeat 2 tablespoons of the oil in a large skillet over medium heat, about 3 minutes. Add the seasoned chicken and cook, turning as needed, until a thermometer inserted in the thickest part of the chicken registers 165°F, about 8 minutes. \nRemove the chicken from the skillet, set aside on a plate, and loosely cover with aluminum foil to keep warm."));
            recipeDao.insert(new Recipe(1, "Maggie's Beer Bolognese", "Place chicken liver in a bowl under cold running water until the water runs clear. Heat 2 TBS of olive oil in a fry pan. Place the beef mince in the fry pan Add the Tomato Paste and stir until done."));
            recipeDao.insert(new Recipe(2, "BLue Cheese and Bacon Smash Burger", "To make the bacon jam put the bacon in a small pan over a low-medium heat. Cook for five minutes. Drizzle a little oil into the pan. Put the pucks in the frying pan."));
            recipeDao.insert(new Recipe(3, "Easy 4 Ingredients Pasta", "Cook the pasta in a large pot of salted water until al dente, following package instructions. Meanwhilem melt the butter in a large skillet over medium heat. Add Sage leaves."));            IngredientsDao ingredientsDao = INSTANCE.ingredientsDao();
            ingredientsDao.insert(new Ingredients("Tomato"));
            ingredientsDao.insert(new Ingredients("Cheese"));
            ingredientsDao.insert(new Ingredients("Ham"));
            ingredientsDao.insert(new Ingredients("Pepperoni"));
            ingredientsDao.insert(new Ingredients("Apple"));
            ingredientsDao.insert(new Ingredients("Sugar"));
            ingredientsDao.insert(new Ingredients("Flour"));
            ingredientsDao.insert(new Ingredients("Eggs"));
            ingredientsDao.insert(new Ingredients("Pork"));
            ingredientsDao.insert(new Ingredients("Olive Oil"));
            ingredientsDao.insert(new Ingredients("Apple"));
            ingredientsDao.insert(new Ingredients("Herbs"));
            ingredientsDao.insert(new Ingredients("Chicken"));
            ingredientsDao.insert(new Ingredients("Spaghetti"));
            ingredientsDao.insert(new Ingredients("Salt"));
            ingredientsDao.insert(new Ingredients("Pepper"));
            ingredientsDao.insert(new Ingredients("Chicken Livers"));
            ingredientsDao.insert(new Ingredients("Olive Oil"));
            ingredientsDao.insert(new Ingredients("Beef Mince"));
            ingredientsDao.insert(new Ingredients("Tomato Paste"));
            ingredientsDao.insert(new Ingredients("Beef mince"));
            ingredientsDao.insert(new Ingredients("Sesame burger buns"));
            ingredientsDao.insert(new Ingredients("Olive Oil"));
            ingredientsDao.insert(new Ingredients("Gherkins"));
            ingredientsDao.insert(new Ingredients("Linguine Pasta"));
            ingredientsDao.insert(new Ingredients("Unsalted Butter"));
            ingredientsDao.insert(new Ingredients("Sage leaves"));
            ingredientsDao.insert(new Ingredients("Lemon"));
            RecipeIngredientDao recipeIngredientDao = INSTANCE.recipeIngredientDao();
            recipeIngredientDao.insert(new RecipeIngredient(1, 1, "2"));
            recipeIngredientDao.insert(new RecipeIngredient(1, 2, "1 Pound"));
            recipeIngredientDao.insert(new RecipeIngredient(1, 3, "2 Pounds"));
            recipeIngredientDao.insert(new RecipeIngredient(1, 4, "500 gr"));
            recipeIngredientDao.insert(new RecipeIngredient(2, 5, "2"));
            recipeIngredientDao.insert(new RecipeIngredient(2, 6, "3 Tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient(2, 7, "1 Pound"));
            recipeIngredientDao.insert(new RecipeIngredient(2, 8, "2"));
            recipeIngredientDao.insert(new RecipeIngredient(3, 9, "1 pork"));
            recipeIngredientDao.insert(new RecipeIngredient(3, 10, "6 Tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient(3, 11, "3"));
            recipeIngredientDao.insert(new RecipeIngredient(3, 12, "3 Tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient(4, 13, "1 Whole Chicken"));
            recipeIngredientDao.insert(new RecipeIngredient(4, 14, "Spaghetti for 2 people"));
            recipeIngredientDao.insert(new RecipeIngredient(4, 15, "1/2 tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient(4, 16, "1 tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient(5, 17, "150g"));
            recipeIngredientDao.insert(new RecipeIngredient(5, 18, "1/2 cup"));
            recipeIngredientDao.insert(new RecipeIngredient(5, 19, "700g"));
            recipeIngredientDao.insert(new RecipeIngredient(5, 20, "500g"));
            recipeIngredientDao.insert(new RecipeIngredient(6, 21, "250g"));
            recipeIngredientDao.insert(new RecipeIngredient(6, 22, "2"));
            recipeIngredientDao.insert(new RecipeIngredient(6, 23, "2 Tbsp"));
            recipeIngredientDao.insert(new RecipeIngredient(6, 24, "1 Small"));
            recipeIngredientDao.insert(new RecipeIngredient(7, 9, "1 Pound"));
            recipeIngredientDao.insert(new RecipeIngredient(7, 10, "1 Stick"));
            recipeIngredientDao.insert(new RecipeIngredient(7, 11, "12 Fresh"));
            recipeIngredientDao.insert(new RecipeIngredient(7, 12, "1"));
            ShoppingListItemDao shoppingListItemDao = INSTANCE.shoppingListItemDao();
            shoppingListItemDao.insert(new ShoppingListItem("banana", "banana.png", "@drawable/banana"));
            shoppingListItemDao.insert(new ShoppingListItem("bread", "bread.png", "@drawable/bread"));
            shoppingListItemDao.insert(new ShoppingListItem("bread2", "bread2.png", "@drawable/bread2"));
            shoppingListItemDao.insert(new ShoppingListItem("broccoli", "broccoli.png", "@drawable/broccoli"));
            shoppingListItemDao.insert(new ShoppingListItem("candy", "candy.png", "@drawable/candy"));
            shoppingListItemDao.insert(new ShoppingListItem("capsicum", "capsicum.png", "@drawable/capsicum"));
            shoppingListItemDao.insert(new ShoppingListItem("celery", "celery.png", "@drawable/celery"));
            shoppingListItemDao.insert(new ShoppingListItem("cheese", "cheese.png", "@drawable/cheese"));
            shoppingListItemDao.insert(new ShoppingListItem("chickenwing", "chickenwing.png", "@drawable/chickenwing"));
            shoppingListItemDao.insert(new ShoppingListItem("cookie", "cookie.png", "@drawable/cookie"));
            shoppingListItemDao.insert(new ShoppingListItem("cornflakes", "cornflakes.png", "@drawable/cornflakes"));
            shoppingListItemDao.insert(new ShoppingListItem("croissant", "croissant.png", "@drawable/croissant"));
            shoppingListItemDao.insert(new ShoppingListItem("donut", "donut.png", "@drawable/donut"));
            shoppingListItemDao.insert(new ShoppingListItem("eggs", "eggs.png", "@drawable/eggs"));
            shoppingListItemDao.insert(new ShoppingListItem("fish", "fish.png", "@drawable/fish"));
            shoppingListItemDao.insert(new ShoppingListItem("fish2", "fish2.png", "@drawable/fish2"));
            shoppingListItemDao.insert(new ShoppingListItem("frenchfries", "frenchfries.png", "@drawable/frenchfries"));
            shoppingListItemDao.insert(new ShoppingListItem("grapes", "grapes.png", "@drawable/grapes"));
            shoppingListItemDao.insert(new ShoppingListItem("ham", "ham.png", "@drawable/ham"));
            shoppingListItemDao.insert(new ShoppingListItem("honey", "honey.png", "@drawable/honey"));
            shoppingListItemDao.insert(new ShoppingListItem("lobster", "lobster.png", "@drawable/lobster"));
            shoppingListItemDao.insert(new ShoppingListItem("mangos", "mangos.png", "@drawable/mangos"));
            shoppingListItemDao.insert(new ShoppingListItem("meat", "meat.png", "@drawable/meat"));
            shoppingListItemDao.insert(new ShoppingListItem("milk", "milk.png", "@drawable/milk"));
            shoppingListItemDao.insert(new ShoppingListItem("orange", "orange.png", "@drawable/orange"));
            shoppingListItemDao.insert(new ShoppingListItem("pizza", "pizza.png", "@drawable/pizza"));
            shoppingListItemDao.insert(new ShoppingListItem("popcorn", " popcorn.png", "@drawable/popcorn"));
            shoppingListItemDao.insert(new ShoppingListItem("potato", "potato.png", "@drawable/potato"));
            shoppingListItemDao.insert(new ShoppingListItem("prawns", "prawns.png", "@drawable/prawns"));
            shoppingListItemDao.insert(new ShoppingListItem("procciutto", "procciutto.png", "@drawable/procciutto"));
            shoppingListItemDao.insert(new ShoppingListItem("sausage", "sausage.png", "@drawable/sausage"));
            shoppingListItemDao.insert(new ShoppingListItem("sodacan", "sodacan.png", "@drawable/sodacan"));
            shoppingListItemDao.insert(new ShoppingListItem("strawberry", "strawberry.png", "@drawable/strawberry"));
            shoppingListItemDao.insert(new ShoppingListItem("waterbottle", "waterbottle.png", "@drawable/waterbottle"));
            shoppingListItemDao.insert(new ShoppingListItem("wholechicken", "wholechicken.png", "@drawable/wholechicken"));
            shoppingListItemDao.insert(new ShoppingListItem("winebottle", "winebottle.png", "@drawable/winebottle"));
            shoppingListItemDao.insert(new ShoppingListItem("winechampagne", "winechampagne.png", "@drawable/winechampagne"));
            shoppingListItemDao.insert(new ShoppingListItem("yogurt", "yogurt.png", "@drawable/yogurt"));
            ShoppingListDao shoppingListDao = INSTANCE.shoppingListDao();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date1 = dateFormat.parse("2024-03-23 12:34:56");
                Date date2 = dateFormat.parse("2024-04-15 11:20:00");
                Date date3 = dateFormat.parse("2024-02-01 09:15:30");
                Date date4 = dateFormat.parse("2024-01-15 10:20:00");
                Date date5 = dateFormat.parse("2024-02-13 07:15:30");

                shoppingListDao.insert(new ShoppingList(1, date1, "Shopping List 1"));
                shoppingListDao.insert(new ShoppingList(1, date2, "Shopping List 2"));
                shoppingListDao.insert(new ShoppingList(2, date3, "Dinner with Friends Shoplist"));
                shoppingListDao.insert(new ShoppingList(2, date4, "Week's Shoplist"));
                shoppingListDao.insert(new ShoppingList(3, date5, "Weekend Party"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ShoppingListItemInListDao shoppingListItemInListDao = INSTANCE.shoppingListItemInListDao();
            shoppingListItemInListDao.insert(new ShoppingListItemInList(1, 23));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(1, 36));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(1, 18));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(1, 17));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(1, 31));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(1, 22));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(1, 19));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(2, 19));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(2, 2));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(2, 20));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(2, 14));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(2, 8));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(2, 12));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(2, 14));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(2, 13));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(2, 10));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(3, 36));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(3, 37));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(3, 34));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(3, 32));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(3, 27));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(3, 26));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(3, 18));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(3, 8));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(4, 2));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(4, 6));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(4, 9));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(4, 11));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(4, 15));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(4, 23));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(4, 13));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(4, 26));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(4, 28));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(4, 25));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(5, 17));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(5, 13));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(5, 5));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(5, 18));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(5, 10));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(5, 8));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(5, 22));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(5, 25));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(5, 26));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(5, 27));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(5, 32));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(5, 36));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(5, 37));
            shoppingListItemInListDao.insert(new ShoppingListItemInList(5, 31));

        });
    }

    private static String encryptPassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
