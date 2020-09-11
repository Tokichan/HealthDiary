package toki.sole.healthdiary;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class RecordInputInterface extends DialogFragment {
    ArrayList<String> types;
    public RecordInputInterface(ArrayList<String> types){
        this.types = types;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Spinner typeSpinner = (Spinner)getActivity().findViewById(R.id.type_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.record_input,R.id.SpinnerTextView,this.types);
        typeSpinner.setAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
