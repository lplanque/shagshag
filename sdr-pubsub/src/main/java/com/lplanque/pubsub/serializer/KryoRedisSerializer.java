package com.lplanque.pubsub.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public final class KryoRedisSerializer<T> implements RedisSerializer<T> {

	private static final Kryo KRYO = new Kryo();
	
	private final Class<T> clazz;
	
	public KryoRedisSerializer(Class<T> clazz) {
		this.clazz = clazz;
		synchronized(KRYO) {
			KRYO.register(clazz);
		}
	}
	
	@Override public byte[] serialize(T in) throws SerializationException {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		final Output output = new Output(os);
		KRYO.writeObject(output, in);
		final byte[] res = output.toBytes();
		output.close();
		return res;
	}

	@Override
	public T deserialize(byte[] out) throws SerializationException {
		final ByteArrayInputStream is = new ByteArrayInputStream(out);
		final Input input = new Input(is);
		final T res = KRYO.readObject(input, clazz);
		input.close();
		return res;
	}
}
