<template>
  <footer
    class="footer"
    :class="{ [`footer-${type}`]: type }"
    :data-background-color="backgroundColor"
  >
    <div class="container">
      <nav>
        <ul>
          <li>
            <a href="#">
              About Us
            </a>
          </li>
          <li>
            <a href="https:///imksh.com">
              Blog
            </a>
          </li>
          <li>
            <a href="https://www.creative-tim.com/license">
              License
            </a>
          </li>
        </ul>
      </nav>
      <div class="copyright">
        &copy; {{ year }}, Coded by
        <a href="https://github.com/CODe1995" target="_blank" rel="noopener"
          >KangSeunghyun</a
        >.
      </div>
    </div>
  </footer>
</template>
<script>
export default {
  props: {
    backgroundColor: String,
    type: String
  },
  data() {
    return {
      year: new Date().getFullYear()
    };
  }
};
</script>
<style></style>
