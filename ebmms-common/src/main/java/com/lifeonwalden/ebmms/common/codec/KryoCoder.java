package com.lifeonwalden.ebmms.common.codec;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Kryo serialization coder
 */
public class KryoCoder {
    private static KryoFactory factory = () -> {
        Kryo kryo = new Kryo();
        return kryo;
    };

    private static KryoPool pool = new KryoPool.Builder(factory).softReferences().build();

    public static Object decodeWithClass(byte[] byteArray) {
        Kryo kryo = pool.borrow();
        Object decoded = kryo.readClassAndObject(new Input(new ByteArrayInputStream(byteArray)));
        pool.release(kryo);

        return decoded;
    }

    public static Object decode(byte[] byteArray, Class clazz) {
        Kryo kryo = pool.borrow();
        Object decoded = kryo.readObject(new Input(new ByteArrayInputStream(byteArray)), clazz);
        pool.release(kryo);

        return decoded;
    }

    public static byte[] encode(Object obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        Kryo kryo = pool.borrow();
        kryo.writeObject(output, obj);
        pool.release(kryo);
        output.flush();

        return baos.toByteArray();
    }

    public static byte[] encodeWithClass(Object obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        Kryo kryo = pool.borrow();
        kryo.writeClassAndObject(output, obj);
        pool.release(kryo);
        output.flush();

        return baos.toByteArray();
    }
}
