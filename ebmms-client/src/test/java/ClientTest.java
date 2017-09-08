import com.lifeonwalden.ebmms.client.Client;
import com.lifeonwalden.ebmms.common.concurrent.MsgStorehouse;
import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.util.UUID;
import org.junit.Test;

public class ClientTest {
    @Test
    public void requestEncodeTest() {
        try {
            Client client = new Client("localhost", 8080, new MsgStorehouse(1024));
            Request request = new Request();
            request.setMethod("update");
            request.setServiceId(1024);
            request.setMsgId(UUID.fetch());
            System.out.println(client.send(request).getErrMsg());
            client.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
