package world.soapboxrace.cli;

import world.soapboxrace.serialbyte.ByteField;
import world.soapboxrace.serialbyte.SerialZebraBytePos;

public class CarProtocol extends SerialZebraBytePos {

	@ByteField(start = 0)
	private short playerId;
	@ByteField(start = 2)
	private short x;
	@ByteField(start = 4)
	private short y;

	public void updateCarProtocol(Car car) {
		playerId = (short) car.getPlayerId();
		x = (short) car.getX();
		y = (short) car.getY();
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = (short) playerId;
	}

	public short getX() {
		return x;
	}

	public short getY() {
		return y;
	}

	public void setY(short y) {
		this.y = y;
	}

	public void setPlayerId(short playerId) {
		this.playerId = playerId;
	}

	public void setX(short x) {
		this.x = x;
	}

	@Override
	public byte[] serialize() {
		try {
			return super.serialize();
		} catch (Exception e) {
			System.err.println("CarProtocol serialize error!");
			e.getMessage();
		}
		return null;
	}

}
