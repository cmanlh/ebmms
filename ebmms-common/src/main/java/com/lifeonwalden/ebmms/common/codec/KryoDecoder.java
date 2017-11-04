package com.lifeonwalden.ebmms.common.codec;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

/**
 * Kryo serialization byte decoder
 */
public class KryoDecoder {
    private static KryoFactory factory = () -> {
        Kryo kryo = new Kryo();
        return kryo;
    };

    private KryoPool pool = new KryoPool.Builder(factory).softReferences().build();

    public byte[] encode(Object obj) {
        Kryo kryo = pool.borrow();
        kryo.getDefaultSerializer()
    }
}
