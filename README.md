## Recruitment task

## Purpose

Simple service to fetch all non-fork GitHub repositories owned by the specified username.

### How to run?

Please set github token within `application.yaml`:

```yaml
github:
  api:
    token: token
```

You can create it here -> https://github.com/settings/tokens

`mvn clean package`

`docker build -t git-repo .`

`docker run -p 8080:8080 git-repo`

### Usage

Api is described in `swagger.yaml`

### Infrastructure comments

Everything is public and no security is provided. In real production scenario
all the ecs would be in private subnets, load balancers will be also in the private subnets and the connection between
Api gateway and loadbalancer would be through VPC links. Moreover I would find better naming convention for all the
infrastructure.

To simplify, I didn't use parameters/outputs and also that is why some values are hardcoded like subnets.

`CloudWatchLogsPolicy` was added to automatically create log stream with ecs 

I pushed docker image directly to ECR because I didn't build the whole pipeline.

### Implementation comments

I decided to use github token due to the fact that github has small limit for unauthenticated users, only 60 per hour.
In real world scenario this token will be stored in some secure place like AWS KMS or Vault. 