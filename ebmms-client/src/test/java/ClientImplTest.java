import com.lifeonwalden.ebmms.client.ClientImpl;
import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.concurrent.MsgStorehouse;
import com.lifeonwalden.ebmms.common.util.UUID;
import org.junit.Test;

public class ClientImplTest {
    @Test
    public void requestEncodeTest() {
        try {
            ClientImpl clientImpl = new ClientImpl("localhost", 8080, new MsgStorehouse(1024));
            Request request = new Request();
            request.setMethod("update");
            request.setService("com.lifeonwalden.remote.service.Test");
            request.setMsgId(UUID.fetch());
            System.out.println(clientImpl.send(request).getErrMsg());
            clientImpl.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
