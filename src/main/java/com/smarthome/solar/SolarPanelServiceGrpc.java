package com.smarthome.solar;

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
    comments = "Source: solar.proto")
public final class SolarPanelServiceGrpc {

  private SolarPanelServiceGrpc() {}

  public static final String SERVICE_NAME = "solar.SolarPanelService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.smarthome.solar.GetCurrentProductionRequest,
      com.smarthome.solar.ProductionInfo> getGetCurrentProductionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCurrentProduction",
      requestType = com.smarthome.solar.GetCurrentProductionRequest.class,
      responseType = com.smarthome.solar.ProductionInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.smarthome.solar.GetCurrentProductionRequest,
      com.smarthome.solar.ProductionInfo> getGetCurrentProductionMethod() {
    io.grpc.MethodDescriptor<com.smarthome.solar.GetCurrentProductionRequest, com.smarthome.solar.ProductionInfo> getGetCurrentProductionMethod;
    if ((getGetCurrentProductionMethod = SolarPanelServiceGrpc.getGetCurrentProductionMethod) == null) {
      synchronized (SolarPanelServiceGrpc.class) {
        if ((getGetCurrentProductionMethod = SolarPanelServiceGrpc.getGetCurrentProductionMethod) == null) {
          SolarPanelServiceGrpc.getGetCurrentProductionMethod = getGetCurrentProductionMethod = 
              io.grpc.MethodDescriptor.<com.smarthome.solar.GetCurrentProductionRequest, com.smarthome.solar.ProductionInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "solar.SolarPanelService", "GetCurrentProduction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.smarthome.solar.GetCurrentProductionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.smarthome.solar.ProductionInfo.getDefaultInstance()))
                  .setSchemaDescriptor(new SolarPanelServiceMethodDescriptorSupplier("GetCurrentProduction"))
                  .build();
          }
        }
     }
     return getGetCurrentProductionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.smarthome.solar.MonitorProductionRequest,
      com.smarthome.solar.ProductionInfo> getMonitorProductionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MonitorProduction",
      requestType = com.smarthome.solar.MonitorProductionRequest.class,
      responseType = com.smarthome.solar.ProductionInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.smarthome.solar.MonitorProductionRequest,
      com.smarthome.solar.ProductionInfo> getMonitorProductionMethod() {
    io.grpc.MethodDescriptor<com.smarthome.solar.MonitorProductionRequest, com.smarthome.solar.ProductionInfo> getMonitorProductionMethod;
    if ((getMonitorProductionMethod = SolarPanelServiceGrpc.getMonitorProductionMethod) == null) {
      synchronized (SolarPanelServiceGrpc.class) {
        if ((getMonitorProductionMethod = SolarPanelServiceGrpc.getMonitorProductionMethod) == null) {
          SolarPanelServiceGrpc.getMonitorProductionMethod = getMonitorProductionMethod = 
              io.grpc.MethodDescriptor.<com.smarthome.solar.MonitorProductionRequest, com.smarthome.solar.ProductionInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "solar.SolarPanelService", "MonitorProduction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.smarthome.solar.MonitorProductionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.smarthome.solar.ProductionInfo.getDefaultInstance()))
                  .setSchemaDescriptor(new SolarPanelServiceMethodDescriptorSupplier("MonitorProduction"))
                  .build();
          }
        }
     }
     return getMonitorProductionMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SolarPanelServiceStub newStub(io.grpc.Channel channel) {
    return new SolarPanelServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SolarPanelServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SolarPanelServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SolarPanelServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SolarPanelServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SolarPanelServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getCurrentProduction(com.smarthome.solar.GetCurrentProductionRequest request,
        io.grpc.stub.StreamObserver<com.smarthome.solar.ProductionInfo> responseObserver) {
      asyncUnimplementedUnaryCall(getGetCurrentProductionMethod(), responseObserver);
    }

    /**
     */
    public void monitorProduction(com.smarthome.solar.MonitorProductionRequest request,
        io.grpc.stub.StreamObserver<com.smarthome.solar.ProductionInfo> responseObserver) {
      asyncUnimplementedUnaryCall(getMonitorProductionMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetCurrentProductionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.smarthome.solar.GetCurrentProductionRequest,
                com.smarthome.solar.ProductionInfo>(
                  this, METHODID_GET_CURRENT_PRODUCTION)))
          .addMethod(
            getMonitorProductionMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                com.smarthome.solar.MonitorProductionRequest,
                com.smarthome.solar.ProductionInfo>(
                  this, METHODID_MONITOR_PRODUCTION)))
          .build();
    }
  }

  /**
   */
  public static final class SolarPanelServiceStub extends io.grpc.stub.AbstractStub<SolarPanelServiceStub> {
    private SolarPanelServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SolarPanelServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SolarPanelServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SolarPanelServiceStub(channel, callOptions);
    }

    /**
     */
    public void getCurrentProduction(com.smarthome.solar.GetCurrentProductionRequest request,
        io.grpc.stub.StreamObserver<com.smarthome.solar.ProductionInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetCurrentProductionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void monitorProduction(com.smarthome.solar.MonitorProductionRequest request,
        io.grpc.stub.StreamObserver<com.smarthome.solar.ProductionInfo> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getMonitorProductionMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SolarPanelServiceBlockingStub extends io.grpc.stub.AbstractStub<SolarPanelServiceBlockingStub> {
    private SolarPanelServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SolarPanelServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SolarPanelServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SolarPanelServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.smarthome.solar.ProductionInfo getCurrentProduction(com.smarthome.solar.GetCurrentProductionRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetCurrentProductionMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.smarthome.solar.ProductionInfo> monitorProduction(
        com.smarthome.solar.MonitorProductionRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getMonitorProductionMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SolarPanelServiceFutureStub extends io.grpc.stub.AbstractStub<SolarPanelServiceFutureStub> {
    private SolarPanelServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SolarPanelServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SolarPanelServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SolarPanelServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.smarthome.solar.ProductionInfo> getCurrentProduction(
        com.smarthome.solar.GetCurrentProductionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetCurrentProductionMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CURRENT_PRODUCTION = 0;
  private static final int METHODID_MONITOR_PRODUCTION = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SolarPanelServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SolarPanelServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CURRENT_PRODUCTION:
          serviceImpl.getCurrentProduction((com.smarthome.solar.GetCurrentProductionRequest) request,
              (io.grpc.stub.StreamObserver<com.smarthome.solar.ProductionInfo>) responseObserver);
          break;
        case METHODID_MONITOR_PRODUCTION:
          serviceImpl.monitorProduction((com.smarthome.solar.MonitorProductionRequest) request,
              (io.grpc.stub.StreamObserver<com.smarthome.solar.ProductionInfo>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SolarPanelServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SolarPanelServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.smarthome.solar.SolarPanelProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SolarPanelService");
    }
  }

  private static final class SolarPanelServiceFileDescriptorSupplier
      extends SolarPanelServiceBaseDescriptorSupplier {
    SolarPanelServiceFileDescriptorSupplier() {}
  }

  private static final class SolarPanelServiceMethodDescriptorSupplier
      extends SolarPanelServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SolarPanelServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (SolarPanelServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SolarPanelServiceFileDescriptorSupplier())
              .addMethod(getGetCurrentProductionMethod())
              .addMethod(getMonitorProductionMethod())
              .build();
        }
      }
    }
    return result;
  }
}
