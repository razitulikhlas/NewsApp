package com.example.newsapp.room;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.newsapp.NewsDetailActivity;

public class BookmarkViewSaveDialogFragment extends DialogFragment {
    private static final String TAG_UID = "TAG_UID";
    private static final String TAG_NAME = "TAG_NAME";

    private int uid = -1;
    private String name;

    private ArticleRoomViewModel articleRoomViewModel;

    public static BookmarkViewSaveDialogFragment newInstance(ArticleTableBookmark articleTableBookmark){
        Bundle args = new Bundle();
        args.putInt(TAG_UID,articleTableBookmark.uid);
        args.putString(TAG_NAME, articleTableBookmark.title);
        BookmarkViewSaveDialogFragment dudf = new BookmarkViewSaveDialogFragment();
        dudf.setArguments(args);
        return dudf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(TAG_UID)
                && getArguments().containsKey(TAG_NAME)
        ){
            uid = getArguments().getInt(TAG_UID, -1);
            name = getArguments().getString(TAG_NAME, null);
        }

        articleRoomViewModel = new ViewModelProvider(requireActivity()).get(ArticleRoomViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to View or Delete Data "+name+"?")
                .setPositiveButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("title", name);
                        intent.putExtra("bookmark", true);
                        getActivity().startActivity(intent);
                        //panggil view disini
                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArticleTableBookmark articleTableBookmark = new ArticleTableBookmark();
                        articleTableBookmark.uid = uid;
                        articleTableBookmark.title = name;
                        articleRoomViewModel.deletearticlebookmark(articleTableBookmark);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        return builder.create();
    }
}
