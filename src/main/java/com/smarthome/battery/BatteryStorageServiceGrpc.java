package com.smarthome.battery;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: battery.proto")
public final class BatteryStorageServiceGrpc {

  private BatteryStorageServiceGrpc() {}

  public static final String SERVICE_NAME = "battery.BatteryStorageService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.smarthome.battery.GetBatteryStatusRequest,
      com.smarthome.battery.BatteryStatusInfo> getGetBatteryStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetBatteryStatus",
      requestType = com.smarthome.battery.GetBatteryStatusRequest.class,
      responseType = com.smarthome.battery.BatteryStatusInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.smarthome.battery.GetBatteryStatusRequest,
      com.smarthome.battery.BatteryStatusInfo> getGetBatteryStatusMethod() {
    io.grpc.MethodDescriptor<com.smarthome.battery.GetBatteryStatusRequest, com.smarthome.battery.BatteryStatusInfo> getGetBatteryStatusMethod;
    if ((getGetBatteryStatusMethod = BatteryStorageServiceGrpc.getGetBatteryStatusMethod) == null) {
      synchronized (BatteryStorageServiceGrpc.class) {
        if ((getGetBatteryStatusMethod = BatteryStorageServiceGrpc.getGetBatteryStatusMethod) == null) {
          BatteryStorageServiceGrpc.getGetBatteryStatusMethod = getGetBatteryStatusMethod = 
              io.grpc.MethodDescriptor.<com.smarthome.battery.GetBatteryStatusRequest, com.smarthome.battery.BatteryStatusInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "battery.BatteryStorageService", "GetBatteryStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.smarthome.battery.GetBatteryStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.smarthome.battery.BatteryStatusInfo.getDefaultInstance()))
                  .setSchemaDescriptor(new BatteryStorageServiceMethodDescriptorSupplier("GetBatteryStatus"))
                  .build();
          }
        }
     }
     return getGetBatteryStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.smarthome.battery.BatteryMonitoringRequest,
      com.smarthome.battery.BatteryMonitoringResponse> getMonitorBatteryStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MonitorBatteryStatus",
      requestType = com.smarthome.battery.BatteryMonitoringRequest.class,
      responseType = com.smarthome.battery.BatteryMonitoringResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.smarthome.battery.BatteryMonitoringRequest,
      com.smarthome.battery.BatteryMonitoringResponse> getMonitorBatteryStatusMethod() {
    io.grpc.MethodDescriptor<com.smarthome.battery.BatteryMonitoringRequest, com.smarthome.battery.BatteryMonitoringResponse> getMonitorBatteryStatusMethod;
    if ((getMonitorBatteryStatusMethod = BatteryStorageServiceGrpc.getMonitorBatteryStatusMethod) == null) {
      synchronized (BatteryStorageServiceGrpc.class) {
        if ((getMonitorBatteryStatusMethod = BatteryStorageServiceGrpc.getMonitorBatteryStatusMethod) == null) {
          BatteryStorageServiceGrpc.getMonitorBatteryStatusMethod = getMonitorBatteryStatusMethod = 
              io.grpc.MethodDescriptor.<com.smarthome.battery.BatteryMonitoringRequest, com.smarthome.battery.BatteryMonitoringResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "battery.BatteryStorageService", "MonitorBatteryStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.smarthome.battery.BatteryMonitoringRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.smarthome.battery.BatteryMonitoringResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new BatteryStorageServiceMethodDescriptorSupplier("MonitorBatteryStatus"))
                  .build();
          }
        }
     }
     return getMonitorBatteryStatusMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BatteryStorageServiceStub newStub(io.grpc.Channel channel) {
    return new BatteryStorageServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BatteryStorageServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new BatteryStorageServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BatteryStorageServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new BatteryStorageServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class BatteryStorageServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getBatteryStatus(com.smarthome.battery.GetBatteryStatusRequest request,
        io.grpc.stub.StreamObserver<com.smarthome.battery.BatteryStatusInfo> responseObserver) {
      asyncUnimplementedUnaryCall(getGetBatteryStatusMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.smarthome.battery.BatteryMonitoringRequest> monitorBatteryStatus(
        io.grpc.stub.StreamObserver<com.smarthome.battery.BatteryMonitoringResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getMonitorBatteryStatusMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetBatteryStatusMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.smarthome.battery.GetBatteryStatusRequest,
                com.smarthome.battery.BatteryStatusInfo>(
                  this, METHODID_GET_BATTERY_STATUS)))
          .addMethod(
            getMonitorBatteryStatusMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                com.smarthome.battery.BatteryMonitoringRequest,
                com.smarthome.battery.BatteryMonitoringResponse>(
                  this, METHODID_MONITOR_BATTERY_STATUS)))
          .build();
    }
  }

  /**
   */
  public static final class BatteryStorageServiceStub extends io.grpc.stub.AbstractStub<BatteryStorageServiceStub> {
    private BatteryStorageServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BatteryStorageServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BatteryStorageServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BatteryStorageServiceStub(channel, callOptions);
    }

    /**
     */
    public void getBatteryStatus(com.smarthome.battery.GetBatteryStatusRequest request,
        io.grpc.stub.StreamObserver<com.smarthome.battery.BatteryStatusInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBatteryStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.smarthome.battery.BatteryMonitoringRequest> monitorBatteryStatus(
        io.grpc.stub.StreamObserver<com.smarthome.battery.BatteryMonitoringResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getMonitorBatteryStatusMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class BatteryStorageServiceBlockingStub extends io.grpc.stub.AbstractStub<BatteryStorageServiceBlockingStub> {
    private BatteryStorageServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BatteryStorageServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BatteryStorageServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BatteryStorageServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.smarthome.battery.BatteryStatusInfo getBatteryStatus(com.smarthome.battery.GetBatteryStatusRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetBatteryStatusMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class BatteryStorageServiceFutureStub extends io.grpc.stub.AbstractStub<BatteryStorageServiceFutureStub> {
    private BatteryStorageServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BatteryStorageServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BatteryStorageServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BatteryStorageServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.smarthome.battery.BatteryStatusInfo> getBatteryStatus(
        com.smarthome.battery.GetBatteryStatusRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetBatteryStatusMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_BATTERY_STATUS = 0;
  private static final int METHODID_MONITOR_BATTERY_STATUS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BatteryStorageServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(BatteryStorageServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_BATTERY_STATUS:
          serviceImpl.getBatteryStatus((com.smarthome.battery.GetBatteryStatusRequest) request,
              (io.grpc.stub.StreamObserver<com.smarthome.battery.BatteryStatusInfo>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_MONITOR_BATTERY_STATUS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.monitorBatteryStatus(
              (io.grpc.stub.StreamObserver<com.smarthome.battery.BatteryMonitoringResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class BatteryStorageServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BatteryStorageServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.smarthome.battery.BatteryStorageProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BatteryStorageService");
    }
  }

  private static final class BatteryStorageServiceFileDescriptorSupplier
      extends BatteryStorageServiceBaseDescriptorSupplier {
    BatteryStorageServiceFileDescriptorSupplier() {}
  }

  private static final class BatteryStorageServiceMethodDescriptorSupplier
      extends BatteryStorageServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    BatteryStorageServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (BatteryStorageServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BatteryStorageServiceFileDescriptorSupplier())
              .addMethod(getGetBatteryStatusMethod())
              .addMethod(getMonitorBatteryStatusMethod())
              .build();
        }
      }
    }
    return result;
  }
}
