# Configuration

Emerauth is configured from the ground up based on a `config.edn` resource. You can see a simple example [here](./resources/config.example.dn). The entire configuration can be overwhelming, but bear with us, we'll try to make it all understandable.

## Resource

A resource is anything that can be authorized and then accessed. A resource is identified by a name, and then matched with its options. For example:

```clj
{"/auth/*" {:methods ["get"]}}
```

This declares a resource named `/auth/*` that can only be authorized when the HTTP method is `GET`.

> [!WARNING]
> The resource `/auth/*` may look like a URI resource, but it has nothing to do with HTTP. It is just an identifier, a literal string that can be later used to set it apart from other resources.

A resource is not bound to a protocol. As long as its identifier (name) and its options match, they can define HTTP resources, gRPC resources, graphQL resources, etc.

## Resource Group

These resources by themselves cannot do much. We need to group them so we can identify what user context they're part of. For example, the following resources:

```clj
{"/auth" {:methods ["post" "put"]}
 "/home" {:methods ["get"]}
 "/store" {:methods ["get"]}
 "/store/:product" {:methods ["get"]}
 "/marketplace" {:methods ["get"]}
 "/marketplace/:product" {:methods ["get" "post" "put" "delete"]}}
```

can be grouped and contextualized as:

```clj
{:public {"/auth" {:methods ["post"]}
          "/home" {:methods ["get"]}}

 :protected {"/auth" {:methods ["put"]}
             "/store" {:methods ["get"]}}

 :marketplace {"/marketplace" {:methods ["get"]}
               "/marketplace/:product" {:methods ["get" "post" "put" "delete"]}}}
```

The `:public` and `:protected` resource groups are registered keywords for Emerauth. They are, respectively, resources that anyone can access, and resources that only signedin users can access.

## App

An app is anything that can request Emerauth to authorize an user towards a resource. They are defined simply for identification, and should be mirrored when requested from withing the applications/services themselves.

> [!NOTE]
> With requesting from within, we mean that the frontend service, when requesting Emerauth to authorize a resource, should state who they are.

Apps can (but are not bound to) be your literal services, like Frontend and Backend.

```clj
{:apps {:frontend {}
        :backend {}}}
```

When declaring an application, its necessary to lay out basic information about it, specifically its `:host`, `:uri` and its resources. Joining what we've seen about resources, resource groups and apps, we can finally declare the `:apps` section of our configuration:

```clj
{:apps {:frontend {:host "localhost"
                   :port 8080
                   :uri "/"
                   :resources
                   {:public
                    {"/auth" {:methods ["post"]}
                     "/home" {:methods ["get"]}}
                    :protected
                    {"/auth" {:methods ["put"]}
                     "/store" {:methods ["get"]}}
                    :marketplace
                    {"/marketplace" {:methods ["get"]}
                     "/marketplace/:product"
                     {:methods ["get" "post" "put" "delete"]}}}}
        :backend {:host "localhost"
                  :port 9000
                  :uri "/api"
                  :resources
                  {:public {"HealthCheck" {}
                            "SignUp" {}
                            "SignIn" {}}
                   :protected {"RefreshToken" {}}
                   :store {"GetProducts" {}}
                   :marketplace {"UpdateProduct" {}
                                 "DeleteProduct" {}}}}}}
```

## DB

The DB section simply defines a connection to your specific database. For example:

```clj
{:db {:provider :postgres
      :connection {:host "localhost"
                   :port 5432
                   :dbname "emerauth"
                   :user "emer-user"
                   :password "emer-pass"}}}
```

defines a connection to Postgres.

## Auth

## Roles

The Roles section identifies the type(s) a user can be. These user roles are then binded to predefined resources. For example:

```clj
{:roles {:customer []
         :seller [:frontend/marketplace :backend/marketplace]}}
```

Declares user role `:customer`, which has access to no resource in specific, besides the default ones `:public` and `:protected`. The user role `:seller` is also declared; a seller has access to both resources `:marketplace` from the `:frontend` app, and the `:marketplace` resource, from the `:backend` app.

## Server

---

A working simple configuration can be seen [here](../resources/config.example.edn).
