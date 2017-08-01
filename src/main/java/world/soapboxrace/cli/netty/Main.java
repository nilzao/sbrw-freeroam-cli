package world.soapboxrace.cli.netty;

import java.awt.EventQueue;
import java.net.InetSocketAddress;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.DatagramPacket;
import world.soapboxrace.cli.MainBoard;
import world.soapboxrace.cli.Sender;
import world.soapboxrace.cli.SenderState;

public class Main {

	private static NettyUdpClient client;
	private static InetSocketAddress remoteAddress;
	private static ChannelFuture channelFuture;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainBoard();
			}
		});

		String host = "localhost";
		int port = 9999;

		if (args.length == 2) {
			host = args[0];
			port = Integer.parseInt(args[1]);
		}

		remoteAddress = new InetSocketAddress(host, port);

		client = new NettyUdpClient();

		try {
			channelFuture = client.start();
			String text = "test";
			System.out.println("Client sending message " + text + " to server");
			send(text.getBytes());
			// Wait until the connection is closed.
			channelFuture.channel().closeFuture().sync();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}

	}

	public static void send(byte[] bytes) {
		try {
			if (channelFuture != null && channelFuture.isSuccess() && channelFuture.isDone()) {
				if (SenderState.DISCONNECTED.equals(Sender.getSenderState())) {
					Sender.setSenderState(SenderState.HELLO);
				}
				client.write(new DatagramPacket(Unpooled.copiedBuffer(bytes), remoteAddress));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
