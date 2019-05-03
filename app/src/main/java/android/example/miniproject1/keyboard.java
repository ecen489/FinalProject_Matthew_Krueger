package android.example.miniproject1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class keyboard extends Fragment{

    boolean cleared = false;

    public keyboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keyboard, container, false);
    }

    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        Button one = (Button) getView().findViewById(R.id.one);
        Button two = (Button) getView().findViewById(R.id.two);
        Button three = (Button) getView().findViewById(R.id.three);
        Button four = (Button) getView().findViewById(R.id.four);
        Button five = (Button) getView().findViewById(R.id.five);
        Button six = (Button) getView().findViewById(R.id.six);
        Button seven = (Button) getView().findViewById(R.id.seven);
        Button eight = (Button) getView().findViewById(R.id.eight);
        Button nine = (Button) getView().findViewById(R.id.nine);
        Button zero = (Button) getView().findViewById(R.id.zero);
        Button clear = (Button) getView().findViewById(R.id.clear);
        Button neg = (Button) getView().findViewById(R.id.neg);
        Button back = (Button) getView().findViewById(R.id.back);

        final FastPowerFragment fragment = (FastPowerFragment) getFragmentManager().findFragmentById(R.id.fast_pow_frag);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CalculationActivity) getActivity()).addinput("1");
                if (fragment != null) {
                    fragment.setInput(((CalculationActivity) getActivity()).returninputstring(), ((CalculationActivity) getActivity()).returncurrentinput());
                }
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CalculationActivity) getActivity()).addinput("2");
                if (fragment != null) {
                    fragment.setInput(((CalculationActivity) getActivity()).returninputstring(), ((CalculationActivity) getActivity()).returncurrentinput());
                }
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CalculationActivity) getActivity()).addinput("3");
                if (fragment != null) {
                    fragment.setInput(((CalculationActivity) getActivity()).returninputstring(), ((CalculationActivity) getActivity()).returncurrentinput());
                }
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CalculationActivity) getActivity()).addinput("4");
                if (fragment != null) {
                    fragment.setInput(((CalculationActivity) getActivity()).returninputstring(), ((CalculationActivity) getActivity()).returncurrentinput());
                }
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CalculationActivity) getActivity()).addinput("5");
                if (fragment != null) {
                    fragment.setInput(((CalculationActivity) getActivity()).returninputstring(), ((CalculationActivity) getActivity()).returncurrentinput());
                }
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CalculationActivity) getActivity()).addinput("6");
                if (fragment != null) {
                    fragment.setInput(((CalculationActivity) getActivity()).returninputstring(), ((CalculationActivity) getActivity()).returncurrentinput());
                }
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CalculationActivity) getActivity()).addinput("7");
                if (fragment != null) {
                    fragment.setInput(((CalculationActivity) getActivity()).returninputstring(), ((CalculationActivity) getActivity()).returncurrentinput());
                }
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CalculationActivity) getActivity()).addinput("8");
                if (fragment != null) {
                    fragment.setInput(((CalculationActivity) getActivity()).returninputstring(), ((CalculationActivity) getActivity()).returncurrentinput());
                }
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CalculationActivity) getActivity()).addinput("9");
                if (fragment != null) {
                    fragment.setInput(((CalculationActivity) getActivity()).returninputstring(), ((CalculationActivity) getActivity()).returncurrentinput());
                }
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CalculationActivity) getActivity()).addinput("0");
                if (fragment != null) {
                    fragment.setInput(((CalculationActivity) getActivity()).returninputstring(), ((CalculationActivity) getActivity()).returncurrentinput());
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CalculationActivity) getActivity()).clearit();
                if (fragment != null) {
                    fragment.setInput(((CalculationActivity) getActivity()).returninputstring(), ((CalculationActivity) getActivity()).returncurrentinput());
                }
            }
        });
        neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CalculationActivity) getActivity()).makeneg();
                if (fragment != null) {
                    fragment.setInput(((CalculationActivity) getActivity()).returninputstring(), ((CalculationActivity) getActivity()).returncurrentinput());
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CalculationActivity) getActivity()).backclick();
            }
        });
    }
}
