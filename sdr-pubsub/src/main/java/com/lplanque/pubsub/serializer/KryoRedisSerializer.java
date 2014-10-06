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
		synchronized(KRYO) { // TODO Check !
			KRYO.register(clazz);
		}
	}
	
	@Override public byte[] serialize(T in) throws SerializationException {
		final ByteArrayOutputStream res = new ByteArrayOutputStream();
		final Output output = new Output(new ByteArrayOutputStream());
		KRYO.writeObject(output, in);
		output.close();
		return res.toByteArray();
	}

	@Override
	public T deserialize(byte[] out) throws SerializationException {
		final Input input = new Input(new ByteArrayInputStream(out));
		final T res = KRYO.readObject(input, clazz);
		input.close();
		return res;
	}
}
