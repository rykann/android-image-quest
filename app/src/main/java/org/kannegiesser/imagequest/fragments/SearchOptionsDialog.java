package org.kannegiesser.imagequest.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.kannegiesser.imagequest.R;
import org.kannegiesser.imagequest.models.SearchOptions;


public class SearchOptionsDialog extends DialogFragment {

    private static final String SEARCH_OPTIONS = "searchOptions";

    public interface SearchOptionsDialogListener {
        void onSaveSearchOptions(SearchOptions searchOptions);
    }

    private Spinner spnImageSize;
    private Spinner spnColorFilter;
    private Spinner spnImageType;
    private EditText etSiteFilter;
    private Button btnSave;

    public SearchOptionsDialog() {
        // Required empty public constructor
    }

    public static SearchOptionsDialog newInstance(SearchOptions searchOptions) {
        SearchOptionsDialog fragment = new SearchOptionsDialog();
        Bundle args = new Bundle();
        args.putParcelable(SEARCH_OPTIONS, searchOptions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_options, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spnImageSize = (Spinner) view.findViewById(R.id.spnImageSize);
        spnColorFilter = (Spinner) view.findViewById(R.id.spnColorFilter);
        spnImageType = (Spinner) view.findViewById(R.id.spnImageType);
        etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);

        SearchOptions searchOptions = getArguments().getParcelable(SEARCH_OPTIONS);
        setSpinnerToValue(spnImageSize, searchOptions.imageSize);
        setSpinnerToValue(spnColorFilter, searchOptions.colorFilter);
        setSpinnerToValue(spnImageType, searchOptions.imageType);
        etSiteFilter.setText(searchOptions.siteFilter);

        btnSave = (Button) view.findViewById(R.id.btnSaveOptions);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchOptionsDialogListener listener = (SearchOptionsDialogListener) getActivity();
                listener.onSaveSearchOptions(buildSearchOptions());
                dismiss();
            }
        });
    }

    private SearchOptions buildSearchOptions() {
        SearchOptions searchOptions = new SearchOptions();
        searchOptions.imageSize = spnImageSize.getSelectedItem().toString();
        searchOptions.colorFilter = spnColorFilter.getSelectedItem().toString();
        searchOptions.imageType = spnImageType.getSelectedItem().toString();
        searchOptions.siteFilter = etSiteFilter.getText().toString();
        return searchOptions;
    }

    private void setSpinnerToValue(Spinner spinner, String value) {
        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                index = i;
                break; // terminate loop
            }
        }
        spinner.setSelection(index);
    }
}
