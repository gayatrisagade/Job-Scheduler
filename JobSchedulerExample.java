import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Resource {
    String id;
    int capacity;
    int availableCapacity;

    public Resource(String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.availableCapacity = capacity;
    }
}

class Job implements Comparable<Job> {
    String id;
    int priority;
    int deadline;
    int resourceRequirement;

    public Job(String id, int priority, int deadline, int resourceRequirement) {
        this.id = id;
        this.priority = priority;
        this.deadline = deadline;
        this.resourceRequirement = resourceRequirement;
    }

    @Override
    public int compareTo(Job other) {
        // Define job comparison based on priority and deadline
        if (this.priority != other.priority) {
            return Integer.compare(other.priority, this.priority);
        } else {
            return Integer.compare(this.deadline, other.deadline);
        }
    }
}

class JobScheduler {
    PriorityQueue<Job> jobQueue = new PriorityQueue<>();
    List<Resource> resources = new ArrayList<>();

    public void addResource(Resource resource) {
        resources.add(resource);
    }

    public void submitJob(Job job) {
        jobQueue.offer(job);
    }

    public void scheduleJobs() {
        while (!jobQueue.isEmpty()) {
            Job job = jobQueue.poll();
            allocateResource(job);
        }
    }

    private void allocateResource(Job job) {
        for (Resource resource : resources) {
            if (resource.availableCapacity >= job.resourceRequirement) {
                // Allocate resource to the job
                resource.availableCapacity -= job.resourceRequirement;
                System.out.println("Job " + job.id + " scheduled on Resource " + resource.id);
                return;
            }
        }
        System.out.println("Job " + job.id + " could not be scheduled. Insufficient resources.");
    }
}

public class JobSchedulerExample {
    public static void main(String[] args) {
        // Create resources
        Resource resource1 = new Resource("R1", 10);
        Resource resource2 = new Resource("R2", 15);

        // Create a job scheduler
        JobScheduler jobScheduler = new JobScheduler();
        jobScheduler.addResource(resource1);
        jobScheduler.addResource(resource2);

        // Submit jobs to the scheduler
        jobScheduler.submitJob(new Job("Job1", 1, 5, 8));
        jobScheduler.submitJob(new Job("Job2", 2, 7, 10));
        jobScheduler.submitJob(new Job("Job3", 1, 8, 5));

        // Schedule and allocate resources to jobs
        jobScheduler.scheduleJobs();
    }
}
