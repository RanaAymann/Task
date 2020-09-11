package net.larntech.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserAdapterVH> {

    private List<UserResponse> userResponseList;
    private Context context;
    private ClickedItem clickedItem;

    public UsersAdapter(ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
    }

    public void setData(List<UserResponse> userResponseList) {
        this.userResponseList = userResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new UsersAdapter.UserAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_users,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterVH holder, int position) {

        UserResponse userResponse = userResponseList.get(position);

        String userId = userResponse.getUserId();
        String prefix;
        if(userResponse.isIs_active()){
            prefix = "A";
        }else{
            prefix = "D";
        }

        holder.prefix.setText(prefix);
        holder.userId.setText(userId);
        holder.imageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.ClickedUser(userResponse);
            }
        });

    }

    public interface ClickedItem{
        public void ClickedUser(UserResponse userResponse);
    }

    @Override
    public int getItemCount() {
        return userResponseList.size();
    }

    public class UserAdapterVH extends RecyclerView.ViewHolder {

        TextView userId;
        TextView Id;
        TextView Title;
        TextView Completed;
        

        public UserAdapterVH(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.userId);
            Id = itemView.findViewById(R.id.Id);
            Title = itemView.findViewById(R.id.Title);
            Completed = itemView.findViewById(R.id.Completed);
            
           


        }
    }
}
