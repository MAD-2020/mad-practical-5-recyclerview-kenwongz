package sg.edu.np.mad.mad_recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final String TAG = "DataList";
    private List<String> mdatalist;
    private LayoutInflater minflater;
    private ItemClickListener mlistener;

    public Adapter(Context context, List<String> datalist) {
        this.minflater = LayoutInflater.from(context);
        this.mdatalist = datalist;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String s = mdatalist.get(position);
        holder.mtextview.setText(s);

    }

    @Override
    public int getItemCount() {
        return mdatalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mtextview;

        ViewHolder(View itemView) {
            super(itemView);
            mtextview = itemView.findViewById(R.id.result);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            if (mlistener != null) mlistener.onItemClick(view, getAdapterPosition());
        }
    }

    String getItem(int i){
        return mdatalist.get(i);
    }

    void setClickListener(ItemClickListener mitemclick){
        this.mlistener = mitemclick;
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}


