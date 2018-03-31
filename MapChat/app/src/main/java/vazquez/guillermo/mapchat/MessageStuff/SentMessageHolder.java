package vazquez.guillermo.mapchat.MessageStuff;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import vazquez.guillermo.mapchat.R;

/**
 * Created by guillermo on 3/31/18.
 */

private class SentMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText, nameText;

    SentMessageHolder(View itemView) {
        super(itemView);
        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        nameText = (TextView) itemView.findViewById(R.id.text_message_name);
    }

    void bind(UserMessage message) {
        messageText.setText(message.getMessage());

        // Format the stored timestamp into a readable String using method.
        timeText.setText(Utils.formatDateTime(message.getCreatedAt()));
        nameText.setText(message.getSender().getNickname());
    }
}
