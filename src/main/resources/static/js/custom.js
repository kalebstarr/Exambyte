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
              </div>
          `;
  questionContainer.appendChild(newQuestion);
  questionId++;
});

window.onbeforeunload = function() {
  return "Reloading will discard all changes";
}
