package com.lifeonwalden.ebmms.register;

import com.lifeonwalden.ebmms.client.Client;

import java.util.List;

public interface LiaisonOfficer {
    List<? extends Client> fetchServiceProvider(String serviceFamilyName);


}
