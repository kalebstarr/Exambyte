let questionId = 2;

document
.getElementById("addQuestionButton")
.addEventListener("click", function () {
  const questionContainer =
      document.getElementById("questionContainer");
  const newQuestion = document.createElement("div");
  newQuestion.classList.add("question");
  newQuestion.innerHTML = `
          <div class="border border-secondary rounded p-3">
                <input
                  type="hidden"
                  id="questionId${questionId}"
                  name="questionId"
                  value="${questionId}"
                  required
                />
                <div class="row mb-3">
                  <label for="questionType${questionId}" class="col-sm-2 col-form-label">Type</label>
                  <div class="col-sm-10">
                    <select
                        id="questionType${questionId}"
                        name="questionType"
                        class="form-select"
                        required
                    >
                      <option selected value="">Choose...</option>
                      <option value="Text">Text</option>
                      <option value="Multiple Choice">Multiple Choice</option>
                    </select>
                  </div>
                </div>
                <div class="row mb-3">
                  <label for="questionTitle${questionId}" class="col-sm-2 col-form-label"
                    >Title</label
                  >
                  <div class="col-sm-10">
                    <input
                      type="text"
                      class="form-control"
                      id="questionTitle${questionId}"
                      name="questionTitle"
                      placeholder="Question Title"
                      required
                    />
                  </div>
                </div>
                <div class="row mb-3">
                  <label
                    for="questionDescription${questionId}"
                    class="col-sm-2 col-form-label"
                    >Description</label
                  >
                  <div class="col-sm-10">
                    <textarea
                      class="form-control"
                      id="questionDescription${questionId}"
                      name="questionDescription"
                      placeholder="Question Description"
                      required
                    ></textarea>
                  </div>
                </div>
                <div class="row mb-3 question-mc-${questionId}" style="display: none">
                  <label class="col-sm-2 col-form-label"
                    >Options</label
                  >
                  <div class="col-sm-10" id="optionsContainer${questionId}">
                    <div class="input-group mb-3">
                      <div class="input-group-text">
                        <label for="questions[${questionId}].correctOptions"></label>
                        <input
                          type="checkbox"
                          id="questions[${questionId}].correctOptions"
                          name="questions[${questionId}].correctOptions"
                        />
                      </div>
                      <label for="questions[${questionId}].options"></label>
                      <input
                        type="text"
                        class="form-control"
                        id="questions[${questionId}].options"
                        name="questions[${questionId}].options"
                        placeholder="Option Text"
                      />
                    </div>
                    <button
                        type="button"
                        class="btn btn-outline-secondary"
                        onclick="addOption(${questionId})"
                    >
                      Add Option
                    </button>
                  </div>
                </div>
              </div>
          `;
  questionContainer.appendChild(newQuestion);

  multipleChoiceFoldEnable(questionId);

  questionId++;
});

/*
window.onbeforeunload = function() {
  return "Reloading will discard all changes";
}
*/

function multipleChoiceFoldEnable(questionId) {
  document.getElementById("questionType" + questionId).addEventListener("change", function () {
    const value = this.value;
    const mcFields = document.querySelectorAll(".question-mc-" + questionId);

    if (value === "Multiple Choice") {
      mcFields.forEach(function (field) {
        field.style.display = "flex";
      });
    } else if (value === "Text") {
      // TODO: Remove content from Multiple choice area or do this validation in the controller
      mcFields.forEach(function (field) {
        field.style.display = "none";
      });
    } else {
      mcFields.forEach(function (field) {
        field.style.display = "none";
      });
    }
  });
}
multipleChoiceFoldEnable(1);

function addOption(questionId) {
  const optionsContainer = document.getElementById(
      `optionsContainer${questionId}`
  );
  const optionCount =
      optionsContainer.querySelectorAll(".input-group").length;
  const newOption = document.createElement("div");
  newOption.classList.add("input-group", "mb-3");
  newOption.innerHTML = `
          <div class="input-group-text">
            <input type="checkbox" name="questions[${
      questionId
  }].correctOptions" />
          </div>
          <input
            type="text"
            class="form-control"
            name="questions[${questionId}].options"
            placeholder="Option Text"
            required
          />
        `;
  optionsContainer.insertBefore(
      newOption,
      optionsContainer.lastElementChild
  );
}
