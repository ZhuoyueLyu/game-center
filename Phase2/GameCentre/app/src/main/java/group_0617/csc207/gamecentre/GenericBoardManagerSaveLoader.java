package group_0617.csc207.gamecentre;

import android.util.Log;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.content.Context;

/**
 * The class responsible for saving BoardManager
 */
public class GenericBoardManagerSaveLoader {

    /**
     * The only instance of this class
     */
    private static GenericBoardManagerSaveLoader theSaveLoader = null;

    /**
     * An empty constructor
     */
    private GenericBoardManagerSaveLoader() {
    }

    /**
     * Return the only instance of this class, if does not exist create a new one
     *
     * @return the only instance of this class
     */
    public static GenericBoardManagerSaveLoader getInstance() {

        if (theSaveLoader == null) {
            theSaveLoader = new GenericBoardManagerSaveLoader();
        }
        return theSaveLoader;
    }

    /**
     * Save the given GenericBoardManager and save it in the specified path under given context
     *
     * @param inGenericBoardManager the GenericBoardManager to save
     * @param inSavePath            the path to save the GenericBoardManager to
     * @param context               the context
     */
    public void saveGenericBoardManager(GenericBoardManager inGenericBoardManager, String inSavePath,
                                        Context context) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(inSavePath, Context.MODE_PRIVATE));
            outputStream.writeObject(inGenericBoardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Save Loader", "File write failed: " + e.toString());
        }
    }

    /**
     * Return the GenericBoardManager stored in the given path under given context.
     * Return null if errors occurred in reading or if no object is found.
     *
     * @param inLoadPath the path to load GenericBoardManager from
     * @param context    the context
     * @return the GenericBoardManager stored. null if not found or error occurred
     */
    public GenericBoardManager loadGenericBoardManager(String inLoadPath, Context context) {
        try {
            InputStream inputStream = context.openFileInput(inLoadPath);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                GenericBoardManager myBoardManager = (GenericBoardManager) input.readObject();
                inputStream.close();
                return myBoardManager;
            }
        } catch (FileNotFoundException e) {
            Log.e("Save Loader", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Save Loader", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Save Loader", "File contained unexpected data type: " + e.toString());
        }
        return null;
    }

}
