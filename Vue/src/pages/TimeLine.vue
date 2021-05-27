<template>
  <div>
      <hr>
    <div class="section section-team text-center">
      <div class="container">
          <h1>About us</h1>
        <h2 class="title">프로젝트 팀원을 소개합니다</h2>
        <div class="team">
          <div class="row">
            <div class="col-md-6">
              <div class="team-player zoom">
                <img
                  src="https://tistory1.daumcdn.net/tistory/1819996/attach/46900e467088483080764baf5555991f"
                  alt="Thumbnail Image"
                  class="rounded-circle img-fluid img-raised"
                />
                <h4 class="title">강승현</h4>
                <p class="category text-primary">팀장</p>
                <p class="description">
                  분야를 가리지 않고 개발하고 있습니다!😎<br>
                  짧은 기간에 정말 많은 시간을 쏟아 부었고, 결과물의 퀄리티를 떠나서
                  기술적으로 처음 접한 부분, 새로운 알고리즘을 접하게 되었기에 프로젝트 결과보단 배움에 의미를 두려고 합니다.                  
                  <br>아주 재미있는 프로젝트였습니다😊
                </p>
                <a href="https://github.com/CODe1995" class="btn btn-primary btn-icon btn-round"
                  ><i class="fab fa-github"></i
                ></a>
                <a href="https://www.instagram.com/1d1kkang/" class="btn btn-primary btn-icon btn-round"
                  ><i class="fab fa-instagram"></i
                ></a>
              </div>
            </div>
            <div class="col-md-6">
              <div class="team-player zoom">
                <img
                  src="img/juyeon_profile.jpg"
                  alt="Thumbnail Image"
                  class="rounded-circle img-fluid img-raised"
                />
                <h4 class="title">김주연</h4>
                <p class="category text-primary">팀원</p>
                <p class="description">
                  이번 프로젝트로 정말 많은 경험을 할 수 있었습니다!!<br>
                  항상 잘 풀리진 않았지만 문제를 해결하고 나면 너무 행복했어요😂<br>
                  앞으로도 배울 것이 많겠지만 포기하지 않고 최선을 다하겠습니다!!
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Button, FormGroupInput } from '@/components';
export default {
  name: 'landing',
  bodyClass: 'landing-page',
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput
  },
  data() {
    return {
      form: {
        firstName: '',
        email: '',
        message: ''
      }
    };
  }
};
</script>
<style>
</style>
