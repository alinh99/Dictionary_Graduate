package com.vnuk.tuhocandroid.dictionary.ui.engviet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.vnuk.tuhocandroid.dictionary.R;
import com.vnuk.tuhocandroid.dictionary.adapter.WordListAdapter;
import com.vnuk.tuhocandroid.dictionary.database.DataAccessAnhViet;

import java.util.ArrayList;
import java.util.List;

public class EngVietFragment extends Fragment implements SearchView.OnQueryTextListener {
    private EngVietViewModel mEngVietViewModel;
    private ListView listWord;
    DataAccessAnhViet dataAccess;
    private View root;
    ArrayList<String> anhViet;
    WordListAdapter mWordListAdapter;
    List<String> dictionary;
    private int count = 0;
    SearchView mSearchView;
    ProgressDialog mProgressDialog;
    SharedPreferences.Editor editor;
    boolean isSaved;
    LoadAsync mLoadAsync;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mEngVietViewModel =
                ViewModelProviders.of ( this ).get ( EngVietViewModel.class );
        root = inflater.inflate ( R.layout.fragment_eng_viet, container, false );

        listWord = root.findViewById ( R.id.list_view_word );
        anhXa ();
        dataOpen ();

        SharedPreferences sharedPreferences = getActivity ().getSharedPreferences ( "saveDataAsync", Context.MODE_PRIVATE );
        editor  = sharedPreferences.edit();
        isSaved = sharedPreferences.getBoolean( "isSaved", false);

        if (isSaved){
            mProgressDialog.show ();
        }else {
            mProgressDialog.dismiss ();
        }


        mSearchView.setOnQueryTextListener ( this );
        mSearchView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                mSearchView.setIconified ( false );
            }
        } );

        mSearchView.setOnQueryTextListener ( new SearchView.OnQueryTextListener () {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (anhViet.contains ( query )){
                    mWordListAdapter.getFilter().filter(query);
                }else {
                    Toast.makeText( getActivity (), "No Match found",Toast.LENGTH_LONG).show();
                }
                mWordListAdapter.notifyDataSetChanged ();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mWordListAdapter.getFilter().filter (newText);
                listWord.setVisibility ( View.VISIBLE );
                mWordListAdapter.notifyDataSetChanged ();

                return false;
            }
        } );
        
        mWordListAdapter = new WordListAdapter ( getActivity (), anhViet );
        listWord.setAdapter ( mWordListAdapter );
        new LoadAsync ().execute (  );
        return root;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public class LoadAsync extends AsyncTask<Void, String, Void> {
        @Override
        protected void onPreExecute() {
            mWordListAdapter = (WordListAdapter) listWord.getAdapter ();
            getActivity ().setProgressBarIndeterminate ( false );
            getActivity ().setProgressBarVisibility ( true );
            if (isSaved){
                mProgressDialog.show ();
                editor.putBoolean ( "isSaved",true );
                editor.apply ();
            }else{
                mProgressDialog.dismiss ();
                Toast.makeText ( getActivity (), "All items were added", Toast.LENGTH_SHORT ).show ();
                editor.putBoolean ( "isSaved",false);
                editor.apply ();
            }
        }                                               

        @Override
        protected Void doInBackground(Void... voids) {
            dataAccess.open ();
            dictionary = dataAccess.getWords (1001, 50000);
            for (String word : dictionary) {
                publishProgress ( word );
                try {
                    Thread.sleep ( 10 );
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }
                if (isCancelled ()){
                   mLoadAsync.cancel ( true );
                }
            }
            dataAccess.close ();
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mWordListAdapter.add ( values[0] );
            count++;
            Integer currentStatus =  (int) (((double) count/(dictionary.size ())*100));
            mProgressDialog.setProgress ( currentStatus );
            mProgressDialog.setMessage ( String.valueOf ( currentStatus ) + "%" );
            mWordListAdapter.notifyDataSetChanged ();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgressDialog.dismiss ();

        }
    }

    private void anhXa(){
        listWord = root.findViewById ( R.id.list_view_word );
        mSearchView = root.findViewById ( R.id.search_view );
        dataAccess = new DataAccessAnhViet ( getActivity () );
        mProgressDialog = new ProgressDialog ( getActivity () );
        mLoadAsync = new LoadAsync ();
    }

    public void dataOpen(){
        dataAccess = DataAccessAnhViet.getInstance ( getActivity () );
        dataAccess.open ();
        anhViet = dataAccess.getWords (0, 1000);
        dataAccess.close ();
    }
}
